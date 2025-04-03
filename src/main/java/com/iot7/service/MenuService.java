package com.iot7.service;

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

    private final BusinessUserRepository businessUserRepository;
    private final MenuRepository menuRepository;


    // 🔹 홈에서 카테고리 목록 가져오기
    public List<String> getCategories() {
        return menuRepository.findDistinctCategories();
    }

    // 🔹 홈에서 카테고리에 따른 메뉴 목록 가져오기
    public List<MenuDTO> getMenuByCategory(String category) {
        List<MenuDTO> menus = menuRepository.findMenusByCategory(category);
        if (menus == null || menus.isEmpty()) {
            throw new RuntimeException("해당 카테고리에 대한 메뉴가 없습니다. " + category);
        }
        return menus;
    }

    // ✅  홈에서 키워드로 메뉴 검색 (이름 또는 재료)
    public List<Menu> searchMenus(String keyword) {
        return menuRepository.findByMenuNameContainingIgnoreCaseOrIngredientsContainingIgnoreCase(keyword, keyword);
    }


    // ✅"메가커피 주세요!"  → "메가커피 본점 번호 찾기"
    // ✅"그 번호로 메뉴 가져오기" → "메뉴  프론트에 전달"
    public List<MenuDTO> getMenusByBrandMainBranch(String brandName) {
        Long businessId = businessUserRepository.findMainBusinessIdByName(brandName)
                .orElseThrow(() -> new RuntimeException("본점 정보 없음"));

        return menuRepository.findByBusinessUser_BusinessId(businessId)
                .stream()
                .map(MenuDTO::fromEntity)
                .collect(Collectors.toList());
    }
}




