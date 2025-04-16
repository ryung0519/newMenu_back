package com.iot7.dto;


import com.iot7.entity.Menu;
import lombok.Data;

import java.util.List;


/*상세 페이지에서 필요한 정보들을 담는 파일 */

@Data // ✅ 롬복
public class ProductDetailDTO {
    private String menuName; // 메뉴 이름
    private String category; // 카테고리
    private int price; // 가격
    private int calorie; // 칼로리
    private String ingredients; // 재료 성분
    private boolean dietYn; // 다이어트 유무
    private String description; // 상세 설명
    private String imageUrl; // 사진
    private String businessName; // 브랜드 이름
    private double averageRating; // 평균 별점
    private List<String> combinations; // 같이 먹으면 좋은 조합
    private List<BlogPostDTO> blogPosts; // 네이버 블로그 검색 결과
    private List<YoutubeVideoDTO> youtubeVideos; //  유튜브 영상 정보 리스트

    //private List<ReviewDTO> reviews;


    // ⭐ 엔티티 →  DTO로 변환
    public ProductDetailDTO convertToProductDetailDTO(Menu menu) {
        ProductDetailDTO dto = new ProductDetailDTO();
        dto.setMenuName(menu.getMenuName());
        dto.setCategory(menu.getCategory());
        dto.setPrice(menu.getPrice());

        Float cal = menu.getCalorie(); //  db에 칼로리 안적혀있어서 임시로 0으로 지정
        dto.setCalorie(cal != null ? Math.round(cal) : 0);
        dto.setIngredients(menu.getIngredients());
        dto.setDietYn("Y".equalsIgnoreCase(menu.getDietYn()));
        dto.setDescription(menu.getDescription());
        dto.setImageUrl(menu.getImage());
        dto.setBusinessName(menu.getBusinessUser().getBusinessName());
        dto.setAverageRating(0.0); // 임시값 넣어놈
        dto.setCombinations(List.of()); // 임시 빈리스트 넣어놈

        return dto;
    }

}
