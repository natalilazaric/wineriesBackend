package com.appwineries.Wineries.service.interfac;

import com.appwineries.Wineries.dto.Response;
import com.appwineries.Wineries.entity.Reservation;

import java.time.LocalDate;

public interface InterfaceReservationService {

    Response saveReservation(Long wineryId, Long userId, Reservation reservationRequest);
    Response findReservationById(Long reservationId);
    Response getAllReservations();
    Response cancelReservation(Long reservationId);

    Response getReservationsByWineryId(Long wineryId);

    Response approveReservation(Long reservationId);
    Response pastReservation(Long reservationId);
}


