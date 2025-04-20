package com.appwineries.Wineries.repo;

import com.appwineries.Wineries.dto.ReviewDTO;
import com.appwineries.Wineries.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT new com.appwineries.Wineries.dto.ReviewDTO(r.id, r.text, r.createdAt, r.winery.id, r.user.id, r.reservation.id) FROM Review r WHERE r.winery.id = :wineryId")
    List<ReviewDTO> findByWineryId(Long wineryId);

    @Query("SELECT new com.appwineries.Wineries.dto.ReviewDTO(r.id, r.text, r.createdAt, r.winery.id, r.user.id, r.reservation.id) FROM Review r WHERE r.user.id = :userId")
    List<ReviewDTO> findByUserId(Long userId);
}
