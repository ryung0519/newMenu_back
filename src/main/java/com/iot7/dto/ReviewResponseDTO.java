package com.iot7.dto;

import com.iot7.entity.Review;
import lombok.Getter;

import java.util.List;

@Getter
public class ReviewResponseDTO {
    private final Long menuId;
    private final String userId;
    private final String reviewContent;
    private final Double reviewRating;

    private final String taste;
    private final String amount;
    private final String wouldVisitAgain;
    private final List<String> imageUrls;

    public ReviewResponseDTO(Review review) {
        this.menuId = review.getId().getMenuId();
        this.userId = review.getId().getUserId();
        this.reviewContent = review.getReviewContent();
        this.reviewRating = review.getReviewRating() != null ? review.getReviewRating().doubleValue() : null;
        this.taste = review.getTaste();
        this.amount = review.getAmount();
        this.wouldVisitAgain = review.getWouldVisitAgain();
        this.imageUrls = review.getImageUrls(); // 문자열 -> List 변환은 엔티티에서 처리
    }
}