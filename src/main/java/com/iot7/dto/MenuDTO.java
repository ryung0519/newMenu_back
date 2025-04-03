package com.iot7.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuDTO {
    private Long menuId;
    private String menuName;
    private String category;
    private int price;
    private String description;
    private String image;
    private String brand;


}
