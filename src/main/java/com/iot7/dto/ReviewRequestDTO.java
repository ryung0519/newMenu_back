package com.iot7.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class ReviewRequestDTO {
    private Long menuId;
    private String userId;
    private String reviewContent;
    private Double reviewRating;

    private String taste;
    private String amount;
    private String wouldVisitAgain;
    private List<String> imageUrls; // JSON으로 직렬화되어 저장될 리스트
}
