package com.iot7.controller;
//HomeScreen의 카테고리 및 메뉴 관련 컨트롤러

import com.iot7.dto.ProductDetailDTO;
import com.iot7.entity.Menu;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired; 
import java.util.List;
import com.iot7.service.MenuService;
import com.iot7.dto.MenuDTO;

@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    //✅ 카테고리 목록 - 카테고리 목록 가져옴
    @GetMapping("/categories")
    public ResponseEntity<List<String>> getCategories() {
        List<String> categories = menuService.getCategories();
        return ResponseEntity.ok(categories);
    }

    // ✅ 카테고리별 메뉴 - 카테고리에 따른 메뉴 정보를 가져오는 API(홈화면)
    @GetMapping("")
    public ResponseEntity<List<MenuDTO>> getMenuByCategories(@RequestParam("category") String category) { // path variable방식으로 요청 처리
        List<MenuDTO> menus = menuService.getMenuByCategory(category);
        return ResponseEntity.ok(menus);
    }

    // ✅ 키워드 검색 - 홈 화면 검색창에서 검색했을떄 (이름 or 재료에 포함된 메뉴 반환)
    @GetMapping("/search")
    public ResponseEntity<List<Menu>> searchMenus(@RequestParam("keyword") String keyword) {
        List<Menu> result = menuService.searchMenus(keyword);
        return ResponseEntity.ok(result);
    }



    // ✅ 브랜드 메뉴 필터링 - 본점 기준, 선택된 브랜드의 메뉴 리스트를 사용자에게 반환
    @GetMapping("/brand")
    public ResponseEntity<List<MenuDTO>> getMenusByBrand(@RequestParam String brandName) {
        List<MenuDTO> menus = menuService.getMenusByBrandMainBranch(brandName);
        return ResponseEntity.ok(menus);
    }

    // ✅ 메뉴 ID로 상세 정보 조회 (상세 페이지용)
    @GetMapping("/{id}")
    public ResponseEntity<ProductDetailDTO> getMenuDetail(@PathVariable Long id) {
        ProductDetailDTO dto = menuService.getProductDetailById(id);
        return ResponseEntity.ok(dto);
    }


}





