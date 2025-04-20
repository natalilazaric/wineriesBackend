package com.appwineries.Wineries.service.interfac;

import com.appwineries.Wineries.dto.Response;
import com.appwineries.Wineries.dto.ReviewDTO;

public interface InterfaceReviewService {
    Response addReview(ReviewDTO reviewDTO);

    Response deleteReview(Long reviewId);

    Response getReviewsByWineryId(Long wineryId);

    Response getReviewsByUserId(Long userId);
    Response editReview(Long reviewId, String newText);

}
