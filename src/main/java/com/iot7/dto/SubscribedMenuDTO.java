package com.iot7.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscribedMenuDTO {
    private Long menuId;
    private String menuName;
    private String imageUrl;
    private String description;
    private String brandName;
    private Integer price;

    public SubscribedMenuDTO(Long menuId, String menuName, String imageUrl, String description,
                             String brandName, int price) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.imageUrl = imageUrl;
        this.description = description;
        this.brandName = brandName;
        this.price = price;
    }
}
