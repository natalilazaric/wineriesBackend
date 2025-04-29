package com.appwineries.Wineries.service.impl;

import com.appwineries.Wineries.dto.*;
import com.appwineries.Wineries.entity.Schedule;
import com.appwineries.Wineries.entity.User;
import com.appwineries.Wineries.entity.Winery;
import com.appwineries.Wineries.entity.WineryWine;
import com.appwineries.Wineries.exception.OurException;
import com.appwineries.Wineries.repo.*;
import com.appwineries.Wineries.service.interfac.InterfaceWineryService;
import com.appwineries.Wineries.utils.Utils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class WineryService implements InterfaceWineryService {

    @Autowired
    private WineryRepository wineryRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private WineryWineRepository wineryWineRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;


    private Map<String, String> convertExtras(List<Object[]> rawExtras) {
        Map<String, String> extrasMap = new HashMap<>();
        for (Object[] row : rawExtras) {
            String key = (String) row[0];
            String value = (String) row[1];
            extrasMap.put(key, value);
        }
        return extrasMap;
    }

    private Map<String, String> convertOffers(List<Object[]> rawOffers) {
        Map<String, String> offersMap = new HashMap<>();
        for (Object[] row : rawOffers) {
            String key = (String) row[0];
            String value = (String) row[1];
            offersMap.put(key, value);
        }
        return offersMap;
    }
    @Override
    public Response addNewWinery(Long userId, MultipartFile photo, String name, String location, double latitude, double longitude, boolean food, String description, List<String> wines, Map<String, String> extras, Map<String, String> offers) {
        Response response = new Response();
        try{
            User owner = userRepository.findById(userId).orElseThrow(()-> new OurException("User not found"));
            if (wineryRepository.existsByOwner(owner)) {
                throw new OurException("Korisnik već ima vinariju!");
            }

            Winery winery = new Winery();

            List<WineryWine> wineryWines = new ArrayList<>();
            for (String wine : wines) {
                WineryWine wineryWine = new WineryWine();
                wineryWine.setWineName(wine);
                wineryWine.setWinery(winery); // Postavi vezu s vinarijom
                wineryWines.add(wineryWine);
            }
            String photoBase64 = Base64.getEncoder().encodeToString(photo.getBytes());
            winery.setPhoto(photoBase64);
            winery.setOwner(owner);
            winery.setName(name);
            winery.setFood(food);
            winery.setDescription(description);
            winery.setLocation(location);
            winery.setLatitude(latitude);
            winery.setLongitude(longitude);
            winery.setWines(wineryWines);
            winery.setExtras(extras);
            winery.setOffers(offers);

            Winery savedWinery = wineryRepository.save(winery);
            WineryDTO wineryDTO = Utils.mapWineryEntityToWineryDTO(savedWinery);
            response.setStatusCode(200);
            response.setMessage("Successful");
            response.setWinery(wineryDTO);

        }catch (Exception ex){
            response.setStatusCode(500);
            response.setMessage("Error adding a new winery " + ex.getMessage());
        }
        return response;
    }

    @Override
    public Response getWinesByWineryId(Long wineryId) {
        Response response = new Response();
        try{
            List<WineryWine> wineryWineList = wineryWineRepository.findByWineryId(wineryId);

            List<String> wineNames = wineryWineList.stream()
                    .map(WineryWine::getWineName)  // Izdvoji samo wineName
                    .collect(Collectors.toList());

            response.setStatusCode(200);
            response.setMessage("Successful");
            response.setWineNames(wineNames);
        }catch (Exception ex) {
            response.setStatusCode(500);
            response.setMessage("Error getting wines for winery ID " + wineryId + ": " + ex.getMessage());
        }

        return response;
    }

    @Override
    public Response getAllWineries() {
        Response response = new Response();
        try {
            List<AllWineriesDTO> wineryList = wineryRepository.findAllWineries();

            for (AllWineriesDTO wineryDTO : wineryList) {
                List<Object[]> rawExtras = wineryRepository.findExtrasRawByWineryId(wineryDTO.getId());
                Map<String, String> extras = convertExtras(rawExtras);
                wineryDTO.setExtras(extras);
                List<Object[]> rawOffers = wineryRepository.findOffersRawByWineryId(wineryDTO.getId());
                Map<String, String> offers = convertOffers(rawOffers);
                wineryDTO.setOffers(offers);

            }

            response.setStatusCode(200);
            response.setMessage("Successful");
            response.setWineryList(wineryList);
        }catch (Exception ex){
            response.setStatusCode(500);
            response.setMessage("Error getting all wineries " + ex.getMessage());
        }
        return response;
    }

    @Override
    public Response deleteWinery(Long wineryId) {
        Response response = new Response();
        try{
            wineryRepository.findById(wineryId).orElseThrow(()-> new OurException("Winery not found"));
            wineryRepository.deleteById(wineryId);
            wineryWineRepository.deleteByWineryId(wineryId);
            response.setStatusCode(200);
            response.setMessage("Successful");
        }catch (OurException ex){
            response.setStatusCode(404);
            response.setMessage(ex.getMessage());
        }catch (Exception ex){
            response.setStatusCode(500);
            response.setMessage("Error deleting a winery " + ex.getMessage());
        }
        return response;
    }


    @Override
    @Transactional
    public Response updateWinery(Long wineryId, AllWineriesDTO wineryDTO, List<String> wines) {
        Response response = new Response();
        try{
            Winery winery = wineryRepository.findById(wineryId).orElseThrow(()-> new OurException("Winery not found"));
            winery.setName(wineryDTO.getName());
            winery.setLocation(wineryDTO.getLocation());
            winery.setDescription(wineryDTO.getDescription());
            winery.setFood(wineryDTO.isFood());
            winery.setLatitude(wineryDTO.getLatitude());
            winery.setLongitude(wineryDTO.getLongitude());
            winery.setExtras(wineryDTO.getExtras());
            winery.setOffers(wineryDTO.getOffers());



            wineryWineRepository.deleteByWineryId(wineryId);
            List<WineryWine> wineryWines = new ArrayList<>();
            for (String wine : wines) {
                WineryWine wineryWine = new WineryWine();
                wineryWine.setWineName(wine);
                wineryWine.setWinery(winery); // Postavi vezu s vinarijom
                wineryWines.add(wineryWine);
            }
            winery.setWines(wineryWines);
            wineryRepository.save(winery);

            response.setStatusCode(200);
            response.setMessage("Successful");

        }catch (OurException ex){
            response.setStatusCode(404);
            response.setMessage(ex.getMessage());
        }catch (Exception ex){
            response.setStatusCode(500);
            response.setMessage("Error updateing a winery " + ex.getMessage());
        }
        return response;
    }

    @Override
    public Response getWineryById(Long wineryId) {
        Response response = new Response();
        try{
            AllWineriesDTO wineryDTO = wineryRepository.findWineryById(wineryId);
            List<Object[]> rawExtras = wineryRepository.findExtrasRawByWineryId(wineryId);
            Map<String, String> extras = convertExtras(rawExtras);
            wineryDTO.setExtras(extras);

            List<Object[]> rawOffers = wineryRepository.findOffersRawByWineryId(wineryId);
            Map<String, String> offers = convertOffers(rawOffers);
            wineryDTO.setOffers(offers);

            response.setStatusCode(200);
            response.setMessage("Successful");
            response.setAllWineriesDTO(wineryDTO);

        }catch (OurException ex){
            response.setStatusCode(404);
            response.setMessage(ex.getMessage());
        }catch (Exception ex){
            response.setStatusCode(500);
            response.setMessage("Error getting a winery by id " + ex.getMessage());
        }
        return response;
    }

    @Override
    public Response getWineryByUserId(Long userId) {
        Response response = new Response();
        try{
            User user = userRepository.findById(userId).orElseThrow(()-> new OurException("Winery not found"));
            Winery winery = wineryRepository.findByOwnerId(userId);

            if (winery == null) {
                throw new OurException("Winery not found");
            }

            WineryDTO wineryDTO = Utils.mapWineryEntityToWineryDTO(winery);

            response.setStatusCode(200);
            response.setMessage("Successful");
            response.setWinery(wineryDTO);

        }catch (OurException ex){
            response.setStatusCode(404);
            response.setMessage(ex.getMessage());
        }catch (Exception ex){
            response.setStatusCode(500);
            response.setMessage("Error getting a winery by user id " + ex.getMessage());
        }
        return response;
    }

    @Override
    public Response addSchedule(Long wineryId, List<ScheduleDTO> scheduleDTOs) {
        Response response = new Response();
        try {
            Winery winery = wineryRepository.findById(wineryId).orElseThrow(()-> new OurException("Winery not found"));

            List<Schedule> schedules = new ArrayList<>();
            for(ScheduleDTO dto : scheduleDTOs) {
                LocalTime startTime = LocalTime.parse(dto.getStartTime());
                LocalTime endTime = LocalTime.parse(dto.getEndTime());

                Schedule schedule = new Schedule();
                schedule.setWinery(winery);
                schedule.setDayOfWeek(dto.getDayOfWeek());
                schedule.setStartTime(startTime);
                schedule.setEndTime(endTime);
                schedule.setMaxGuests(dto.getMaxGuests());
                schedule.setMaxReservations(dto.getMaxReservations());

                schedules.add(schedule);
            }

            scheduleRepository.saveAll(schedules);
            response.setStatusCode(200);
            response.setMessage("Time slots successfully added.");
            response.setSchedule(schedules);


        }catch (Exception ex){
            response.setStatusCode(500);
            response.setMessage("Error adding a schedule " + ex.getMessage());
        }
        return response;
    }

    @Override
    public Response getScheduleByWineryId(Long wineryId) {
        Response response = new Response();
        try {
            List<ScheduleDTO> scheduleList = scheduleRepository.findByWineryId(wineryId);
            response.setStatusCode(200);
            response.setMessage("Successful");
            response.setScheduleList(scheduleList);
        }catch (Exception ex){
            response.setStatusCode(500);
            response.setMessage("Error getting all schedule " + ex.getMessage());
        }
        return response;
    }

    @Override
    public Response updateSchedule(Long wineryId, List<ScheduleDTO> scheduleDTOs) {
        Response response = new Response();
        try {
            Winery winery = wineryRepository.findById(wineryId).orElseThrow(()-> new OurException("Winery not found"));
            scheduleRepository.deleteByWineryId(wineryId);

            List<Schedule> schedules = new ArrayList<>();
            for(ScheduleDTO dto : scheduleDTOs) {
                LocalTime startTime = LocalTime.parse(dto.getStartTime());
                LocalTime endTime = LocalTime.parse(dto.getEndTime());

                Schedule schedule = new Schedule();
                schedule.setWinery(winery);
                schedule.setDayOfWeek(dto.getDayOfWeek());
                schedule.setStartTime(startTime);
                schedule.setEndTime(endTime);
                schedule.setMaxGuests(dto.getMaxGuests());
                schedule.setMaxReservations(dto.getMaxReservations());

                schedules.add(schedule);
            }

            scheduleRepository.saveAll(schedules);
            response.setStatusCode(200);
            response.setMessage("Time slots successfully added.");
        }catch (Exception ex){
            response.setStatusCode(500);
            response.setMessage("Error getting all schedule " + ex.getMessage());
        }
        return response;
    }

}
