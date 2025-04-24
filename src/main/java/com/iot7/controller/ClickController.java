package com.iot7.controller;

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

    @GetMapping("/popular")
    public ResponseEntity<List<MenuDTO>> getPopularMenusByBrand(@RequestParam String brandName) {
        List<MenuDTO> popularMenus = clickService.getPopularMenusByBrand(brandName);
        return ResponseEntity.ok(popularMenus);
    }
}