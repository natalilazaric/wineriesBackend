package com.appwineries.Wineries.controller;

import com.appwineries.Wineries.dto.Response;
import com.appwineries.Wineries.dto.ReviewDTO;
import com.appwineries.Wineries.service.interfac.InterfaceReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private InterfaceReviewService interfaceReviewService;

    @PostMapping("/add")
        public ResponseEntity<Response> addReview(@RequestBody ReviewDTO reviewDTO) {
        Response response = interfaceReviewService.addReview(reviewDTO);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/delete-review/{reviewId}")
    public ResponseEntity<Response> deleteReview(@PathVariable Long reviewId) {
        Response response = interfaceReviewService.deleteReview(reviewId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get-review-by-wineryid/{wineryId}")
    public ResponseEntity<Response> getReviewsForWinery(@PathVariable Long wineryId) {
        Response response = interfaceReviewService.getReviewsByWineryId(wineryId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get-review-by-userId/{userId}")
    public ResponseEntity<Response> getReviewsByUserId(@PathVariable Long userId) {
        Response response = interfaceReviewService.getReviewsByUserId(userId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/edit-review/{reviewId}")
    public ResponseEntity<Response> editReview(@PathVariable Long reviewId, @RequestBody String text) {
        Response response = interfaceReviewService.editReview(reviewId, text);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
