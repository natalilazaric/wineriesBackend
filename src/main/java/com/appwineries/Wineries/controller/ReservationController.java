package com.appwineries.Wineries.controller;

import com.appwineries.Wineries.dto.Response;
import com.appwineries.Wineries.entity.Reservation;
import com.appwineries.Wineries.service.interfac.InterfaceReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private InterfaceReservationService interfaceReservationService;


    @PostMapping("/save/{wineryId}/{userId}")
    public ResponseEntity<Response> saveReservation(
            @PathVariable("wineryId") Long wineryId,
            @PathVariable("userId") Long userId,
            @RequestBody Reservation reservationRequest
    ){
        Response response = interfaceReservationService.saveReservation(wineryId,userId,reservationRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/approve/{reservationId}")
    public ResponseEntity<Response> approveReservation(@PathVariable("reservationId") Long reservationId){
        Response response = interfaceReservationService.approveReservation(reservationId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/past/{reservationId}")
    public ResponseEntity<Response> pastReservation(@PathVariable("reservationId") Long reservationId){
        Response response = interfaceReservationService.pastReservation(reservationId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<Response> getAllReservations(){
        Response response = interfaceReservationService.getAllReservations();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/reservation-by-id/{reservationId}")
    public ResponseEntity<Response> getReservationById(@PathVariable("reservationId") Long reservationId){
        Response response = interfaceReservationService.findReservationById(reservationId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/reservation-by-wineryid/{wineryId}")
    public ResponseEntity<Response> getReservationByWineryId(@PathVariable("wineryId") Long wineryId){
        Response response = interfaceReservationService.getReservationsByWineryId(wineryId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/delete/{reservationId}")
    @PreAuthorize("hasAuthority('WINEMAKER') or hasAuthority('USER')")
    public ResponseEntity<Response> cancelReservation(@PathVariable("reservationId") Long reservationId){
        Response response = interfaceReservationService.cancelReservation(reservationId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
