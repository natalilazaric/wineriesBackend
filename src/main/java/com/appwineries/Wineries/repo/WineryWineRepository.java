package com.appwineries.Wineries.repo;

import com.appwineries.Wineries.entity.WineryWine;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WineryWineRepository extends JpaRepository<WineryWine, Long> {

    List<WineryWine> findByWineryId(Long wineryId);

    void deleteByWineryId(Long wineryId);
}
