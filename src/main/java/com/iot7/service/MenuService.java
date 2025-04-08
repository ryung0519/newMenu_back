package com.iot7.service;

import com.iot7.dto.CalendarMenuDTO;
import com.iot7.dto.MenuDTO;
import com.iot7.entity.Menu;
import com.iot7.repository.BusinessUserRepository;
import com.iot7.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final BusinessUserRepository businessUserRepository;

    // 🔹 카테고리 목록 가져오기
    public List<String> getCategories() {
        return menuRepository.findDistinctCategories();
    }

    // 🔹 선택한 카테고리의 메뉴 목록 가져오기 (DTO 방식 반환)
    public List<MenuDTO> getMenuByCategory(String category) {
        List<MenuDTO> menus = menuRepository.findMenusByCategory(category);
        if (menus == null || menus.isEmpty()) {
            throw new RuntimeException("해당 카테고리에 대한 메뉴가 없습니다. " + category);
        }
        return menus;
    }

    // ✅ 홈에서 키워드로 메뉴 검색
    public List<Menu> searchMenus(String keyword) {
        return menuRepository.findByMenuNameContainingIgnoreCaseOrIngredientsContainingIgnoreCase(keyword, keyword);
    }

    // ✅ 브랜드 필터링 - 본점 기반 메뉴 가져오기
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
                        menu.getMenuName(),
                        menu.getCategory(),
                        menu.getRegDate(),
                        menu.getBrand(),
                        menu.getDescription(),
                        menu.getPrice(),
                        menu.getImage()
                ))
                .collect(Collectors.toList());
    }
}
