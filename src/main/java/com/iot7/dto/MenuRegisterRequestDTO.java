package com.iot7.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuRegisterRequestDTO {
    private String menuName;
    private String category;
    private int price;
    private float calorie;
    private boolean dietYn;
    private String ingredients;
    private String imageUrl;
    private String description;
}
