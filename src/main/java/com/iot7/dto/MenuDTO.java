package com.iot7.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class MenuDTO {
    private Long menuId;
    private String menuName;
    private String category;
    private int price;

    // ✅ 생성자 추가
    public MenuDTO(Long menuId, String menuName, String category, int price) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.category = category;
        this.price = price;
    }
}
