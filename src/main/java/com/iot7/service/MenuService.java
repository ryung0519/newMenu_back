package com.iot7.service;

import com.iot7.dto.MenuDTO;
import com.iot7.entity.Menu;
import com.iot7.repository.MenuRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {

    private final MenuRepository menuRepository;

    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

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

    // 🔹✅  홈에서 키워드로 메뉴 검색 (이름 또는 재료)
    public List<Menu> searchMenus(String keyword) {
        return menuRepository.findByMenuNameContainingIgnoreCaseOrIngredientsContainingIgnoreCase(keyword, keyword);
    }
}
