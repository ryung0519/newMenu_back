package com.iot7.dto;

import com.iot7.entity.Menu;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class LocalMenuDTO {
    private Long menuId;
    private String menuName;
    private String category;
    private Integer price;
    private String location;
    private String brand;
    private String imageUrl;



    public LocalMenuDTO(Menu menu, String location) {
        this.menuId = menu.getMenuId();
        this.menuName = menu.getMenuName();
        this.category = menu.getCategory();
        this.price = menu.getPrice();
        this.location = location;
        this.brand = menu.getBusinessUser().getBusinessName();
        this.imageUrl = menu.getImage();

    }
}
