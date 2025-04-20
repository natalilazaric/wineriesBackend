package com.appwineries.Wineries.repo;

import com.appwineries.Wineries.dto.AllWineriesDTO;
import com.appwineries.Wineries.entity.User;
import com.appwineries.Wineries.entity.Winery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface WineryRepository extends JpaRepository<Winery, Long> {

    @Query("SELECT DISTINCT w.name FROM Winery w")
    List<String> findByName();

    @Query("SELECT w FROM Winery w ORDER BY w.id DESC")
    List<Winery> getAllWineries();

    @Query("SELECT new com.appwineries.Wineries.dto.AllWineriesDTO(w.id, w.name, w.location, w.latitude, w.longitude, w.price, w.food, w.description, w.photo) FROM Winery w ORDER BY w.id DESC")
    List<AllWineriesDTO> findAllWineries();

    @Query("SELECT new com.appwineries.Wineries.dto.AllWineriesDTO(w.id, w.name, w.location, w.latitude, w.longitude, w.price, w.food, w.description, w.photo) FROM Winery w WHERE w.id = :wineryId")
    AllWineriesDTO findWineryById(@Param("wineryId") Long wineryId);

    Winery findByOwnerId(Long ownerId);

    boolean existsByOwner(User owner);

    @Query(value = "SELECT title, value FROM winery_extras WHERE winery_id = :wineryId", nativeQuery = true)
    List<Object[]> findExtrasRawByWineryId(@Param("wineryId") Long wineryId);
}
