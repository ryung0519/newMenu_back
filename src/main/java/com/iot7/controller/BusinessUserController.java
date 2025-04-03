package com.iot7.controller;

import com.iot7.service.BusinessUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

//브랜드 필터링할때 필요한 파일

@RestController
@RequestMapping("/brand")
@RequiredArgsConstructor
public class BusinessUserController {

    private final BusinessUserService businessUserService;

    // ✅ 본점기준,브랜드 이름 리스트 프론트에 보여주기
    @GetMapping("/list")
    public ResponseEntity<List<String>> getBrandNames() {
        List<String> brandNames = businessUserService.getAllMainBranchBrandNames();
        return ResponseEntity.ok(brandNames);
    }
}
