package com.iot7.service;

import com.iot7.dto.MenuDTO;
import com.iot7.entity.Menu;
import com.iot7.repository.ClickRepository;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor; // ✅ 이거 반드시 추가!
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ClickService {
    private final ClickRepository clickRepository;

    public List<MenuDTO> getPopularMenusByBrand(String brandName) {
        List<Menu> menus = clickRepository.findPopularMenusByBrand(brandName);
        return menus.stream()
                .map(MenuDTO::fromEntity)
                .collect(Collectors.toList());
    }
}