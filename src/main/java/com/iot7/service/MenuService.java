package com.iot7.service;

import com.iot7.dto.CalendarMenuDTO;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.stream.Collectors;

import com.iot7.dto.CalendarMenuDTO;
import com.iot7.repository.MenuRepository;
import com.iot7.dto.MenuDTO;

import static java.awt.SystemColor.menu;

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

    //캘린더에서 사용할 전체 메뉴데이터 반환
    public List<CalendarMenuDTO> getAllMenuForCalendar(){
        return menuRepository.findAll().stream()
                .map(menu -> new CalendarMenuDTO(
                        menu.getMenuName(),
                        menu.getCategory(),
                        menu.getRegDate()
                ))
                .collect(Collectors.toList());
    }
}
