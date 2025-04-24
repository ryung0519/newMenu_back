package com.iot7.dto;

import com.iot7.entity.Menu;
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
    private String businessName;
    private String description;
    private String image;
    private String brand;
    private String imageUrl;
    private Float averageRating;
    private Long clickCount;

    //fromEntity()에서 사용하는 생성자 파라미터 순서와 개수는
    // MenuDTO 생성자와 정확히 일치해야 함!

    // ✅ 생성자 추가
    public MenuDTO(Long menuId, String menuName, String category, int price, String businessName, String imageUrl, String description, Float averageRating, Long clickCount) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.category = category;
        this.price = price;
        this.businessName = businessName;
        this.imageUrl = imageUrl;
        this.description = description;
        this.averageRating = averageRating;
        this.clickCount = clickCount;

    }


    // ⭐ 필터링 위해서 필요한 엔티티 →  DTO로 변환
    public static MenuDTO fromEntity(Menu menu) {
        return new MenuDTO(
                menu.getMenuId(),
                menu.getMenuName(),
                menu.getCategory(),
                menu.getPrice(),
                menu.getBusinessUser().getBusinessName(),
                menu.getImage(),
                menu.getDescription(),
                menu.getAverageRating(),
                menu.getClickCount()
        );
    }
}