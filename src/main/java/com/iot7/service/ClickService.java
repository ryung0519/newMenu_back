package com.iot7.service;

import com.iot7.dto.MenuDTO;
import com.iot7.entity.Menu;
import com.iot7.repository.ClickRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ClickService {
    private final ClickRepository clickRepository;

    public List<MenuDTO> getPopularMenusByBrand(String brandName) {
        LocalDateTime twoWeeksAgo = LocalDateTime.now().minusDays(14); //2주전부터 오늘까지 날짜 계산
        List<Menu> menus = clickRepository.findPopularMenusByBrand(brandName, twoWeeksAgo);
        return menus.stream()
                .map(MenuDTO::fromEntity)
                .collect(Collectors.toList());
    }
}