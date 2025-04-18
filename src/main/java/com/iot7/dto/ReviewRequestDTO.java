package com.iot7.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ReviewRequestDTO {
    private Long menuId;
    private String userId;
    private String reviewContent;
    private Double reviewRating;
}
