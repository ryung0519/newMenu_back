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


    // ✅ 생성자 추가
    public MenuDTO(Long menuId, String menuName, String category, int price, String businessName,String imageUrl, String description) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.category = category;
        this.price = price;
        this.businessName = businessName;
        this.imageUrl = imageUrl; // ✅ 이미지 필드 연결
        this.description = description;
    }

    // ⭐ 필터링 위해서 필요한 엔티티 →  DTO로 변환
    public static MenuDTO fromEntity(Menu menu) {
        if (menu == null) return null;

        String businessName = menu.getBusinessUser() != null
                ? menu.getBusinessUser().getBusinessName()
                : "알 수 없음";

        MenuDTO dto = new MenuDTO(
                menu.getMenuId(),
                menu.getMenuName(),
                menu.getCategory(),
                menu.getPrice(),
                businessName,
                menu.getImage(),
                menu.getDescription()

        );

        dto.setBrand(businessName); // 브랜드 이름으로도 동일하게 설정
//        dto.setBrand(menu.getBusinessUser().getBusinessName());
        dto.setImageUrl(menu.getImage()); // ✅ 상세페이지에서 브랜드 눌렀을때 이미지 받아오기 위해 추가
        return dto;
    }
}