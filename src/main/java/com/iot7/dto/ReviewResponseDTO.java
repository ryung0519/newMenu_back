package com.iot7.dto;

import com.iot7.entity.Review;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
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
    private final LocalDateTime createdAt;

    @Setter
    private String pairedMenuName;

    // ✅ pairedMenuName 있는 버전
    public ReviewResponseDTO(Review review, String pairedMenuName) {
        this.menuId = review.getId().getMenuId();
        this.userId = review.getId().getUserId();
        this.reviewContent = review.getReviewContent();
        this.reviewRating = review.getReviewRating() != null ? review.getReviewRating().doubleValue() : null;
        this.taste = review.getTaste();
        this.amount = review.getAmount();
        this.wouldVisitAgain = review.getWouldVisitAgain();
        this.imageUrls = review.getImageUrlList();
        this.createdAt = review.getCreatedAt();
        this.pairedMenuName = pairedMenuName;
    }

    // ✅ pairedMenuName 없는 기본 생성자도 필요할 수 있음
    public ReviewResponseDTO(Review review) {
        this.menuId = review.getId().getMenuId();
        this.userId = review.getId().getUserId();
        this.reviewContent = review.getReviewContent();
        this.reviewRating = review.getReviewRating() != null ? review.getReviewRating().doubleValue() : null;
        this.taste = review.getTaste();
        this.amount = review.getAmount();
        this.wouldVisitAgain = review.getWouldVisitAgain();
        this.imageUrls = review.getImageUrlList();
        this.createdAt = review.getCreatedAt();
    }
}
