package com.iot7.controller;

import com.iot7.entity.Menu;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired; 
import java.util.List;
import com.iot7.service.MenuService;
import com.iot7.dto.MenuDTO;

@RestController
@RequestMapping("/menu")
public class MenuController{
    @Autowired
    private MenuService menuService;

    //카테고리 목록 가져옴
    @GetMapping("/categories")
    public ResponseEntity<List<String>> getCategories(){
        List<String> categories = menuService.getCategories();
        return ResponseEntity.ok(categories);
    }

    //카테고리에 따른 메뉴 정보를 가져오는 API(홈화면)
    @GetMapping("")
    public ResponseEntity<List<MenuDTO>> getMenuByCategories(@RequestParam("category") String category ){ // path variable방식으로 요청 처리
        List<MenuDTO> menus = menuService.getMenuByCategory(category);
        return ResponseEntity.ok(menus);
    }

    // ✅ 홈 화면 검색창에서 검색했을떄 (이름 or 재료에 포함된 메뉴 반환)
    @GetMapping("/search")
    public ResponseEntity<List<Menu>> searchMenus(@RequestParam("keyword") String keyword) {
        List<Menu> result = menuService.searchMenus(keyword);
        return ResponseEntity.ok(result);
    }


}