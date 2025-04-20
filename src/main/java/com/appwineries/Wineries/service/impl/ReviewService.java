package com.appwineries.Wineries.service.impl;


import com.appwineries.Wineries.dto.Response;
import com.appwineries.Wineries.dto.ReviewDTO;
import com.appwineries.Wineries.dto.ScheduleDTO;
import com.appwineries.Wineries.dto.UserDTO;
import com.appwineries.Wineries.entity.Review;
import com.appwineries.Wineries.entity.User;
import com.appwineries.Wineries.exception.OurException;
import com.appwineries.Wineries.repo.ReservationRepository;
import com.appwineries.Wineries.repo.WineryRepository;
import com.appwineries.Wineries.repo.ReviewRepository;
import com.appwineries.Wineries.repo.UserRepository;
import com.appwineries.Wineries.service.interfac.InterfaceReviewService;
import com.appwineries.Wineries.utils.Utils;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewService implements InterfaceReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WineryRepository wineryRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    public Response addReview(ReviewDTO reviewDTO) {
        Response response = new Response();
        try{
            Review review = new Review();
            review.setText(reviewDTO.getText());
            review.setCreatedAt(LocalDateTime.now());
            review.setUser(userRepository.findById(reviewDTO.getUserId()).orElseThrow());
            review.setWinery(wineryRepository.findById(reviewDTO.getWineryId()).orElseThrow());
            if (reviewDTO.getReservationId() != null) {
                review.setReservation(reservationRepository.findById(reviewDTO.getReservationId()).orElse(null));
            }
            Review saved = reviewRepository.save(review);
            reviewDTO.setCreatedAt(review.getCreatedAt());
            response.setStatusCode(200);
            response.setMessage("Successful");
            response.setReview(reviewDTO);


        }catch (Exception ex){
            response.setStatusCode(500);
            response.setMessage("Error adding a review " + ex.getMessage());
        }
        return response;
    }

    @Override
    public Response deleteReview(Long reviewId) {
        Response response = new Response();
        try{
            reviewRepository.deleteById(reviewId);
            response.setStatusCode(200);
            response.setMessage("Successful");

        }catch (Exception ex){
            response.setStatusCode(500);
            response.setMessage("Error deleting a review " + ex.getMessage());
        }
        return response;
    }

    @Override
    public Response getReviewsByWineryId(Long wineryId) {
        Response response = new Response();
        try{
            List<ReviewDTO> reviews = reviewRepository.findByWineryId(wineryId);
            response.setStatusCode(200);
            response.setMessage("Successful");
            response.setReviewDTOList(reviews);

        }catch (Exception ex){
            response.setStatusCode(500);
            response.setMessage("Error getting reviews by winery id" + ex.getMessage());
        }
        return response;
    }

    @Override
    public Response getReviewsByUserId(Long userId) {
        Response response = new Response();
        try{
            List<ReviewDTO> reviews = reviewRepository.findByUserId(userId);
            response.setStatusCode(200);
            response.setMessage("Successful");
            response.setReviewDTOList(reviews);

        }catch (Exception ex){
            response.setStatusCode(500);
            response.setMessage("Error getting reviews by user id " + ex.getMessage());
        }
        return response;
    }

    @Override
    public Response editReview(Long reviewId, String newText) {
        Response response = new Response();
        try{
            Review review = reviewRepository.findById(reviewId)
                    .orElseThrow(() -> new EntityNotFoundException("Recenzija ne postoji."));

            review.setText(newText);
            review.setCreatedAt(LocalDateTime.now());
            reviewRepository.save(review);
            response.setStatusCode(200);
            response.setMessage("Successful");


        }catch (Exception ex){
            response.setStatusCode(500);
            response.setMessage("Error editing a review " + ex.getMessage());
        }
        return response;
    }
}
