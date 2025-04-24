package com.iot7.controller;

import com.iot7.dto.LocalMenuDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.iot7.service.LocalMenuService;

import java.util.List;

@RestController
@RequestMapping("/api/menus")
public class LocalMenuController {
    private final LocalMenuService localMenuService;

    @Autowired
    public LocalMenuController(LocalMenuService localMenuService) {
        this.localMenuService = localMenuService;
    }

    // 지역명으로 메뉴 검색
    @GetMapping("/by-location")
    public List<LocalMenuDTO> getMenusByLocation(@RequestParam String keyword) {
        return localMenuService.findMenusByLocationKeyword(keyword);
    }

    // 좌표로 메뉴 검색
    @GetMapping("/nearby")
    public List<LocalMenuDTO> getNearbyMenus(
            @RequestParam double latitude,
            @RequestParam double longitude,
            @RequestParam(defaultValue = "5.0") double radius) {

        return localMenuService.findMenusByUserLocation(latitude, longitude, radius);
    }
}