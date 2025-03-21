package com.iot7.services;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import com.iot7.repository.MenuRepository;
import com.iot7.dto.MenuDTO;

@Service
public class MenuService {
    @Autowired
    private MenuRepository menuRepository;

    //카테고리 목록 가져옴
    public List<String> getCategories(){
        return menuRepository.findDistinctCategories();
    }
    //선택한 카테고리의 메뉴 목록 가져옴(DTO방식으로 반환)
    public List<MenuDTO> getMenuByCategory(String category){
        List<MenuDTO> menus = menuRepository.findMenusByCategory(category);
        if( menus == null || menus.isEmpty()){
            throw new RuntimeException("해당 카테고리에 대한 메뉴가 없습니다. "+category);
        }
        return menus;
    }
}
