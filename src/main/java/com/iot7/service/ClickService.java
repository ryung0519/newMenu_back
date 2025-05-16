package com.iot7.service;

import com.iot7.dto.HotMenuDTO;
import com.iot7.dto.MenuDTO;
import com.iot7.dto.SearchBarRankDTO;
import com.iot7.entity.Menu;
import com.iot7.repository.ClickRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ClickService {
    private final ClickRepository clickRepository;

    //최근 2주동안 해당 브랜드의 인기 제품
    public List<MenuDTO> getPopularMenusByBrand(String brandName) {
        LocalDateTime twoWeeksAgo = LocalDateTime.now().minusDays(14); //2주전부터 오늘까지 날짜 계산
        List<Menu> menus = clickRepository.findPopularMenusByBrand(brandName, twoWeeksAgo);
        return menus.stream()
                .map(MenuDTO::fromEntity)
                .collect(Collectors.toList());
    }
    //최근 1주동안 전체 브랜드의 핫한 제품
    public List<HotMenuDTO> getHotMenus() {
        LocalDateTime oneWeekAgo = LocalDateTime.now().minusDays(7);

        return clickRepository.findTop7HotMenus(oneWeekAgo).stream() // 1주전부터 오늘까지 클릭수 상위 7개 가져옴
                .map(row -> new HotMenuDTO(
                        ((Number) row[0]).longValue(),     // menuId
                        (String) row[1],                    // menuName
                        (String) row[2]                     // imageUrl
                ))
                .collect(Collectors.toList());
    }

    //하루동안 전체 브랜드 클릭수 많았던 제품
    public List<SearchBarRankDTO> getHotKeywords() {
        LocalDateTime oneDayAgo = LocalDateTime.now().minusDays(1);

        return clickRepository.findTop10HotKeywords(oneDayAgo).stream()
                .map(row -> new SearchBarRankDTO((String) row[0])) // menuName만 DTO에 담기
                .collect(Collectors.toList());
    }


}