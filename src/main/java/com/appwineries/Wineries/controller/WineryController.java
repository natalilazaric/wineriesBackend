package com.appwineries.Wineries.controller;

import com.appwineries.Wineries.dto.AllWineriesDTO;
import com.appwineries.Wineries.dto.Response;
import com.appwineries.Wineries.dto.ScheduleDTO;
import com.appwineries.Wineries.dto.UpdateWineryDTO;
import com.appwineries.Wineries.service.interfac.InterfaceWineryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("/wineries")
public class WineryController {

    @Autowired
    private InterfaceWineryService interfaceWineryService;


    @PostMapping("/add/{userId}")
    public ResponseEntity<Response> addNewWinery(
            @PathVariable("userId") Long userId,
            @RequestParam(value="photo", required = false)MultipartFile photo,
            @RequestParam(value="name", required = false)String name,
            @RequestParam(value="location", required = false)String location,
            @RequestParam(value="latitude", required = false)double latitude,
            @RequestParam(value="longitude", required = false)double longitude,
            @RequestParam(value="food", required = false)boolean food,
            @RequestParam(value="description", required = false)String description,
            @RequestParam(name = "wines", required = false) String winesString,
            @RequestParam(name = "extras", required = false) String extrasJson,
            @RequestParam(name = "offers", required = false) String offersJson
            ){


        List<String> wines = winesString != null ? Arrays.asList(winesString.split(",")) : new ArrayList<>();
        Map<String, String> extras = new HashMap<>();
        if (extrasJson != null && !extrasJson.isEmpty()) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                extras = objectMapper.readValue(extrasJson, Map.class);
            } catch (Exception e) {
                Response error = new Response();
                error.setStatusCode(400);
                error.setMessage("Neispravan format dodatnih informacija (extras). Očekivan JSON objekt.");
                return ResponseEntity.status(400).body(error);
            }
        }
        Map<String, String> offers = new HashMap<>();
        if (offersJson != null && !offersJson.isEmpty()) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                offers = objectMapper.readValue(offersJson, Map.class);
            } catch (Exception e) {
                Response error = new Response();
                error.setStatusCode(400);
                error.setMessage("Neispravan format dodatnih informacija (extras). Očekivan JSON objekt.");
                return ResponseEntity.status(400).body(error);
            }
        }
        System.out.println("OFFERS JSON: " + offersJson);
        System.out.println("OFFERS : " + offers);

        Response response = interfaceWineryService.addNewWinery(userId,photo, name, location, latitude, longitude, food, description, wines, extras, offers);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<Response> getAllWineries(){
        Response response = interfaceWineryService.getAllWineries();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/wines/{wineryId}")
    public ResponseEntity<Response> getWinesByWineryId(@PathVariable("wineryId") Long wineryId){
        Response response = interfaceWineryService.getWinesByWineryId(wineryId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/winery-by-id/{wineryId}")
    public ResponseEntity<Response> getWineryById(@PathVariable("wineryId") Long wineryId){
        Response response = interfaceWineryService.getWineryById(wineryId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/winery-by-userid/{userId}")
    public ResponseEntity<Response> getWineryByUserId(@PathVariable("userId") Long userId){
        Response response = interfaceWineryService.getWineryByUserId(userId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/delete/{wineryId}")
    public ResponseEntity<Response> deleteWinery(@PathVariable("wineryId") Long wineryId){
        Response response = interfaceWineryService.deleteWinery(wineryId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/schedule/{wineryId}")
    public ResponseEntity<Response> addSchedule(@PathVariable("wineryId") Long wineryId, @RequestBody ScheduleDTO[] scheduleDTOsArray){

        System.out.println("POST /schedule/{wineryId} endpoint hit!");

        List<ScheduleDTO> scheduleDTOs = Arrays.asList(scheduleDTOsArray);
        System.out.println("Received schedule DTOs:");
        scheduleDTOs.forEach(dto -> System.out.println(dto));

        Response response = interfaceWineryService.addSchedule(wineryId, scheduleDTOs);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/scheduleAll/{wineryId}")
    public ResponseEntity<Response> getScheduleByWineryId(@PathVariable("wineryId") Long wineryId){
        Response response = interfaceWineryService.getScheduleByWineryId(wineryId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/update-winery/{wineryId}")
    public ResponseEntity<Response> updateWinery(@PathVariable Long wineryId, @RequestBody UpdateWineryDTO updateWineryDTO) {
        AllWineriesDTO wineryDTO = updateWineryDTO.getWinery();
        String winesString = updateWineryDTO.getWines();

        List<String> wines = winesString != null ? Arrays.asList(winesString.split(",")) : new ArrayList<>();
        System.out.println("wines u listi su: " + wines);

        Response response = interfaceWineryService.updateWinery(wineryId, wineryDTO, wines);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/update-schedule/{wineryId}")
    public ResponseEntity<Response> updateSchedule(@PathVariable Long wineryId, @RequestBody List<ScheduleDTO> schedules) {
        Response response = interfaceWineryService.updateSchedule(wineryId, schedules);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }











}
