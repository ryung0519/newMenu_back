package com.iot7.controller;

import com.iot7.dto.HotMenuDTO;
import com.iot7.dto.MenuDTO;
import com.iot7.service.ClickService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.iot7.dto.SearchBarRankDTO;


import java.util.List;

@RestController
@RequestMapping("/click")
@RequiredArgsConstructor
public class ClickController {

    private final ClickService clickService;


    //최근 2주 클릭수 기준 해당 브랜드의 인기 제품
    @GetMapping("/popular")
    public ResponseEntity<List<MenuDTO>> getPopularMenusByBrand(@RequestParam String brandName) {
        List<MenuDTO> popularMenus = clickService.getPopularMenusByBrand(brandName);
        return ResponseEntity.ok(popularMenus);
    }

    //최근 1주 클릭수 기준 전체 브랜드의 핫한 제품
    @GetMapping("/hot")
    public ResponseEntity<List<HotMenuDTO>> getHotMenus() {
        return ResponseEntity.ok(clickService.getHotMenus());
    }

    //최근 1일 클릭수 기준 급상승 검색어 TOP 10
    @GetMapping("/hot-keywords")
    public ResponseEntity<List<SearchBarRankDTO>> getHotKeywords() {
        return ResponseEntity.ok(clickService.getHotKeywords());
    }
}



