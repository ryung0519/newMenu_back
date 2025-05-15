package com.iot7.controller;

import com.iot7.dto.HotMenuDTO;
import com.iot7.dto.MenuDTO;
import com.iot7.service.ClickService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/click")
@RequiredArgsConstructor
public class ClickController {

    private final ClickService clickService;


    //최근 2주동안 해당 브랜드의 인기 제품
    @GetMapping("/popular")
    public ResponseEntity<List<MenuDTO>> getPopularMenusByBrand(@RequestParam String brandName) {
        List<MenuDTO> popularMenus = clickService.getPopularMenusByBrand(brandName);
        return ResponseEntity.ok(popularMenus);
    }

    //최근 1주동안 전체 브랜드의 핫한 제품
    @GetMapping("/hot")
    public ResponseEntity<List<HotMenuDTO>> getHotMenus() {
        return ResponseEntity.ok(clickService.getHotMenus());
    }
}