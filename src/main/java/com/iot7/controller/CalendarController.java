package com.iot7.controller;

import com.iot7.dto.ProductDetailDTO;
import com.iot7.service.MenuService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.iot7.dto.CalendarMenuDTO;

import java.util.List;

@RestController
@RequestMapping("/calendar")
@CrossOrigin(origins = "*")
public class CalendarController {
    private final MenuService menuService;

    public CalendarController(MenuService menuService) {
        this.menuService = menuService;
    }
    @GetMapping("/menus")
    public List<CalendarMenuDTO> getCalendarMenus(){
        return menuService.getAllMenuForCalendar();
    }

    // ✅ 메뉴 ID로 상세 정보 조회 (상세 페이지용)
    @GetMapping("/{id}")
    public ResponseEntity<ProductDetailDTO> getMenuDetail(@PathVariable Long id) {
        ProductDetailDTO dto = menuService.getProductDetailById(id);
        return ResponseEntity.ok(dto);
    }
}
