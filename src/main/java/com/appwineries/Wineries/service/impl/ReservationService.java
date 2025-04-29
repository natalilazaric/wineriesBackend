package com.appwineries.Wineries.service.impl;

import com.appwineries.Wineries.dto.AllWineriesDTO;
import com.appwineries.Wineries.dto.ReservationDTO;
import com.appwineries.Wineries.dto.Response;
import com.appwineries.Wineries.dto.WineryDTO;
import com.appwineries.Wineries.entity.Reservation;
import com.appwineries.Wineries.entity.User;
import com.appwineries.Wineries.entity.Winery;
import com.appwineries.Wineries.exception.OurException;
import com.appwineries.Wineries.repo.ReservationRepository;
import com.appwineries.Wineries.repo.UserRepository;
import com.appwineries.Wineries.repo.WineryRepository;
import com.appwineries.Wineries.service.interfac.InterfaceReservationService;
import com.appwineries.Wineries.service.interfac.InterfaceWineryService;
import com.appwineries.Wineries.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ReservationService implements InterfaceReservationService {

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private WineryRepository wineryRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private InterfaceWineryService interfaceWineryService;
    @Autowired
    private EmailService emailService;

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
    public Response saveReservation(Long wineryId, Long userId, Reservation reservationRequest) {
        Response response = new Response();
        try{

            List<Object[]> rawOffers = wineryRepository.findOffersRawByWineryId(wineryId);
            Map<String, String> offers = convertOffers(rawOffers);

            Winery winery = wineryRepository.findById(wineryId).orElseThrow(()-> new OurException("Winery not found"));
            User user = userRepository.findById(userId).orElseThrow(()-> new OurException("User not found"));

            String offerDescription = reservationRequest.getOffer();
            String priceString = offers.get(offerDescription);
            if (priceString == null) {
                throw new OurException("Offer not found: " + offerDescription);
            }

            // Parsiranje cijene i izračun totalne cijene
            BigDecimal pricePerGuest = new BigDecimal(priceString);
            BigDecimal totalPrice = pricePerGuest.multiply(BigDecimal.valueOf(reservationRequest.getNumberOfGuests()));

            reservationRequest.setTotalPrice(totalPrice);
            reservationRequest.setWinery(winery);
            reservationRequest.setUser(user);
            reservationRepository.save(reservationRequest);
            response.setStatusCode(200);
            response.setMessage("Successful");

        }catch (OurException ex){
            response.setStatusCode(404);
            response.setMessage(ex.getMessage());
        }catch (Exception ex){
            response.setStatusCode(500);
            response.setMessage("Error saving the reservation " + ex.getMessage());
        }
        return response;
    }


    @Override
    public Response findReservationById(Long reservationId) {
        Response response = new Response();
        try{
            Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(() -> new OurException("Reservation not found"));
            ReservationDTO reservationDTO = Utils.mapReservationEntityToReservationDTO(reservation);
            response.setStatusCode(200);
            response.setMessage("Successful");
            response.setReservation(reservationDTO);

        }catch (OurException ex){
            response.setStatusCode(404);
            response.setMessage(ex.getMessage());
        }catch (Exception ex){
            response.setStatusCode(500);
            response.setMessage("Error finding the reservation by id " + ex.getMessage());
        }
        return response;
    }

    @Override
    public Response getAllReservations() {
        Response response = new Response();
        try{
            List<Reservation> reservationList = reservationRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
            List<ReservationDTO> reservationDTOList = Utils.mapReservationListEntityToReservationListDTO(reservationList);
            response.setStatusCode(200);
            response.setMessage("Successful");
            response.setReservationList(reservationDTOList);

        }catch (OurException ex){
            response.setStatusCode(404);
            response.setMessage(ex.getMessage());
        }catch (Exception ex){
            response.setStatusCode(500);
            response.setMessage("Error getting all reservations " + ex.getMessage());
        }
        return response;
    }

    @Override
    public Response cancelReservation(Long reservationId) {
        Response response = new Response();
        try{
            reservationRepository.findById(reservationId).orElseThrow(()-> new OurException("Reservation does not exist"));
            reservationRepository.deleteById(reservationId);
            response.setStatusCode(200);
            response.setMessage("Successful");

        }catch (OurException ex){
            response.setStatusCode(404);
            response.setMessage(ex.getMessage());
        }catch (Exception ex){
            response.setStatusCode(500);
            response.setMessage("Error canceling a reservation " + ex.getMessage());
        }
        return response;
    }

    @Override
    public Response getReservationsByWineryId(Long wineryId) {
        Response response = new Response();
        try{
            List<Reservation> reservations = reservationRepository.findByWineryId(wineryId);
            List<ReservationDTO> reservationDTOList = Utils.mapReservationListEntityToReservationListDTO(reservations);
            response.setReservationList(reservationDTOList);
            response.setStatusCode(200);
            response.setMessage("Successful");

        }catch (OurException ex){
            response.setStatusCode(404);
            response.setMessage(ex.getMessage());
        }catch (Exception ex){
            response.setStatusCode(500);
            response.setMessage("Error canceling a reservation " + ex.getMessage());
        }
        return response;
    }

    @Override
    public Response approveReservation(Long reservationId) {
        Response response = new Response();
        try{
            Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(()-> new OurException("Reservation does not exist"));
            if (reservation.getState().equals("PENDING")) {
                reservation.setState("APPROVED");
                reservationRepository.save(reservation);
                ReservationDTO reservationDTO = Utils.mapReservationEntityToReservationDTO(reservation);

                emailService.sendConfirmationEmail(
                        reservation.getUser().getEmail(),
                        "Ime vinarije: " + reservation.getWinery().getName() +
                                "\nLokacija: " + reservation.getWinery().getLocation() +
                                "\nDatum rezervacije: " + reservation.getDate() +
                                "\nTermin: " + reservation.getStartTime() + " - " + reservation.getEndTime() +
                                "\nOffer: " + reservation.getOffer() +
                                "\nBroj gostiju: " + reservation.getNumberOfGuests() +
                                "\nUkupna cijena: " + reservation.getTotalPrice()
                );

                response.setStatusCode(200);
                response.setMessage("Successful");
                response.setReservation(reservationDTO);
            }
        }catch (OurException ex){
            response.setStatusCode(404);
            response.setMessage(ex.getMessage());
        }catch (Exception ex){
            response.setStatusCode(500);
            response.setMessage("Error approving a reservation " + ex.getMessage());
        }
        return response;
    }

    @Override
    public Response pastReservation(Long reservationId) {
        Response response = new Response();
        try{
            Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(()-> new OurException("Reservation does not exist"));
            if (reservation.getState().equals("APPROVED")) {
                reservation.setState("FINISHED");
                reservationRepository.save(reservation);
                ReservationDTO reservationDTO = Utils.mapReservationEntityToReservationDTO(reservation);

                response.setStatusCode(200);
                response.setMessage("Successful");
                response.setReservation(reservationDTO);
            }
        }catch (OurException ex){
            response.setStatusCode(404);
            response.setMessage(ex.getMessage());
        }catch (Exception ex){
            response.setStatusCode(500);
            response.setMessage("Error approving a reservation " + ex.getMessage());
        }
        return response;
    }
}
