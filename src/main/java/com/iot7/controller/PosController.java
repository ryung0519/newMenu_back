package com.iot7.controller;


import com.iot7.dto.PosDTO;
import com.iot7.service.PosService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pos")
public class PosController {

    private final PosService posService;


    // 요리 재료 갖다놓기 (생성자 주입)
    public PosController(PosService posService) {
        this.posService = posService;
    }

    // 가까운 매장 5개 조회 API
    @GetMapping("/nearest")
    public List<PosDTO> getNearestStores(
            @RequestParam String brandName,// 매장명!
            @RequestParam double userLat,
            @RequestParam double userLng
    ) {
        return posService.findNearestStoresByBrand(brandName, userLat, userLng);
    }
}
