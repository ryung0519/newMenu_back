package com.iot7.dto;

import com.iot7.entity.Review;
import lombok.Getter;

@Getter
public class ReviewResponseDTO {
    private final Long menuId;
    private final String userId;
    private final String reviewContent;
    private final Double reviewRating;

    public ReviewResponseDTO(Review review) {
        this.menuId = review.getId().getMenuId();
        this.userId = review.getId().getUserId();
        this.reviewContent = review.getReviewContent();
        this.reviewRating = review.getReviewRating() != null ? review.getReviewRating().doubleValue() : null;
    }
}