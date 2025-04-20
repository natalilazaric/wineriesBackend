package com.appwineries.Wineries.repo;

import com.appwineries.Wineries.dto.ScheduleDTO;
import com.appwineries.Wineries.entity.Schedule;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query("SELECT new com.appwineries.Wineries.dto.ScheduleDTO(s.dayOfWeek, s.startTime, s.endTime, s.maxGuests, s.maxReservations) FROM Schedule s WHERE s.winery.id = :wineryId")
    List<ScheduleDTO> findByWineryId(Long wineryId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Schedule s WHERE s.winery.id = :wineryId")
    void deleteByWineryId(@Param("wineryId") Long wineryId);
}
