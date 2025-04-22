package com.iot7.service;
import org.springframework.beans.factory.annotation.Value;
import com.iot7.dto.YoutubeDTO;
import com.iot7.dto.BlogDTO;
import com.iot7.dto.CalendarMenuDTO;
import com.iot7.dto.MenuDTO;
import com.iot7.dto.ProductDetailDTO;
import com.iot7.entity.Menu;
import com.iot7.repository.BusinessUserRepository;
import com.iot7.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList; // ⭐ 추가
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final BusinessUserRepository businessUserRepository;

    private final NaverService naverService; // ✅ 네이버 서비스 주입
    private final YoutubeService youtubeService; // ✅ 유튜브 서비스 주입

    @Value("${youtube.api.key}") //유튜브 키값
    private String youtubeApiKey;

    @Value("${naver.api.client-id}") // 네이버 키값
    private String naverClientId;

    @Value("${naver.api.client-secret}")
    private String naverClientSecret;


    // ✅ 카테고리 목록 가져오기
    public List<String> getCategories() {
        return menuRepository.findDistinctCategories();
    }

    // ✅ 선택한 카테고리의 메뉴 목록 가져오기 (DTO 방식 반환)
    public List<MenuDTO> getMenuByCategory(String category) {
        List<MenuDTO> menus = menuRepository.findMenusByCategory(category); // ✅ 바로 DTO로 받기

        if (menus == null || menus.isEmpty()) {
            System.out.println(" 해당 카테고리에 대한 메뉴가 없습니다. " + category);
            return List.of();
        }

        return menus;
    }


    // ✅ 홈에서 메뉴 검색
    public List<Menu> searchMenus(String keyword) {
        return menuRepository.findByMenuNameContainingIgnoreCaseOrIngredientsContainingIgnoreCase(keyword, keyword);
    }


    // ✅ 브랜드 필터링
    public List<MenuDTO> getMenusByBrandMainBranch(String brandName) {
        Long businessId = businessUserRepository.findMainBusinessIdByName(brandName)  // 본점 브랜드의 businiess_id를 가져옴
                .orElseThrow(() -> new RuntimeException("본점 정보 없음"));

        return menuRepository.findByBusinessUser_BusinessId(businessId)  // 그 아이디로 menu 조회한뒤,
                .stream()                                               // MENUDTO로 변환한뒤 리스트로 반환뒤 리턴
                .map(MenuDTO::fromEntity)
                .collect(Collectors.toList());
    }


    // ✅ 캘린더용 전체 메뉴 데이터 반환
    public List<CalendarMenuDTO> getAllMenuForCalendar() {
        return menuRepository.findAll().stream()
                .map(menu -> new CalendarMenuDTO(
                        menu.getMenuId(),
                        menu.getMenuName(),
                        menu.getCategory(),
                        menu.getRegDate(),
                        menu.getBusinessUser().getBusinessName(),
                        menu.getDescription(),
                        menu.getPrice(),
                        menu.getImage()
                ))
                .collect(Collectors.toList());
    }


    // ✅ 상세페이지에서 메뉴 ID로 상세 정보 조회
    public ProductDetailDTO getProductDetailById(Long menuId) {
        Menu menu = menuRepository.findById(menuId) // 1) 메뉴 정보 db에서 가져옴
                .orElseThrow(() -> new RuntimeException("해당 메뉴를 찾을 수 없습니다."));


        /*menu 에 있는 것들을 바탕으로 new dto 를 만들어서, 거기에 블로그 글도 추가하는 흐름*/

        ProductDetailDTO dto = new ProductDetailDTO().convertToProductDetailDTO(menu); // 2) 메뉴 정보를 기반으로 새로운 dto 만들기
        // convert 포장용지

         // ✅ 블로그 + 유튜브 API에 검색어 보내기 위한 쿼리 - 브랜드명 + 메뉴명
        String query = menu.getBusinessUser().getBusinessName() + " " + menu.getMenuName();

        try {
            List<BlogDTO> blogPosts = naverService.fetchBlogPosts(query); // ✅ 네이버 서비스에서 가져옴
            dto.setBlogPosts(blogPosts); // dto에 블로그 글까지 추가로 담기
        } catch (Exception e) {
            e.printStackTrace();
            dto.setBlogPosts(new ArrayList<>()); // 실패 시 빈 리스트 반환
        }

        try {
            List<YoutubeDTO> youtubeVideos = youtubeService.fetchYoutubeVideos(query); // ✅ 유튜브 영상 조회
            dto.setYoutubeVideos(youtubeVideos);
        } catch (Exception e) {
            e.printStackTrace();
            dto.setYoutubeVideos(new ArrayList<>()); // 실패 시 빈값
        }


        return dto;

    }

}


