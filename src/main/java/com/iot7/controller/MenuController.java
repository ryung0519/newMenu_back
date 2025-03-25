package com.iot7.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired; 
import java.util.List;
import com.iot7.services.MenuService;
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
}