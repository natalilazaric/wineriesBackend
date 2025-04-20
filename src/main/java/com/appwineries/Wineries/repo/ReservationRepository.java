package com.appwineries.Wineries.repo;

import com.appwineries.Wineries.dto.ReservationDTO;
import com.appwineries.Wineries.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByWineryId(Long wineryId);
    List<Reservation> findByUserId(Long userId);

}
