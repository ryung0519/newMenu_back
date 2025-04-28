package com.iot7.service;

import com.iot7.dto.LocalMenuDTO;
import com.iot7.entity.Menu;
import com.iot7.entity.MenuPos;
import com.iot7.entity.Pos;
import com.iot7.repository.MenuPosRepository;
import com.iot7.repository.MenuRepository;
import com.iot7.repository.PosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.HashSet;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LocalMenuService {
    private final PosRepository posRepository;
    private final MenuRepository menuRepository;
    private final MenuPosRepository menuPosRepository;

    // 지구 반경 (km)
    private static final double EARTH_RADIUS_KM = 6371.0;

    @Autowired
    public LocalMenuService(PosRepository posRepository,
                            MenuRepository menuRepository,
                            MenuPosRepository menuPosRepository) {
        this.posRepository = posRepository;
        this.menuRepository = menuRepository;
        this.menuPosRepository = menuPosRepository;
    }

    // 지역명으로 메뉴 검색
    public List<LocalMenuDTO> findMenusByLocationKeyword(String locationKeyword) {
        // 위치 검색
        List<Pos> positions = posRepository.findByLocationContaining(locationKeyword);
        return getMenusFromPositions(positions);
    }

    // 사용자 좌표로 메뉴 검색
    public List<LocalMenuDTO> findMenusByUserLocation(double latitude, double longitude, double distanceKm) {
        // 모든 위치 정보 가져오기
        List<Pos> allPositions = posRepository.findAll();

        // 거리 계산 및 필터링(아래에 계산코드)
        List<Pos> nearbyPositions = allPositions.stream()
                .filter(pos -> pos.getLatitude() != null && pos.getLongitude() != null)
                .filter(pos -> calculateDistance(
                        latitude, longitude,
                        pos.getLatitude(), pos.getLongitude()) <= distanceKm)
                .collect(Collectors.toList());

        return getMenusFromPositions(nearbyPositions);
    }

    private List<LocalMenuDTO> getMenusFromPositions(List<Pos> positions) {
        if (positions.isEmpty()) {
            return Collections.emptyList();
        }

        // 현재 지역 POS ID 리스트
        List<Long> posIds = positions.stream()
                .map(Pos::getPosId)
                .collect(Collectors.toList());

        // 판매 중인 메뉴-POS 연결 정보 가져오기
        List<MenuPos> menuPosEntries = menuPosRepository.findByPosIdInAndSaleStatus(posIds, 1);

        if (menuPosEntries.isEmpty()) {
            return Collections.emptyList();
        }

        // POS ID → Location 매핑
        Map<Long, String> posLocationMap = positions.stream()
                .collect(Collectors.toMap(Pos::getPosId, Pos::getLocation));

        // 메뉴 ID → POS ID 리스트 매핑
        Map<Long, List<Long>> menuToPosIdsMap = menuPosEntries.stream()
                .collect(Collectors.groupingBy(MenuPos::getMenuId,
                        Collectors.mapping(MenuPos::getPosId, Collectors.toList())));

        // 지역 POS ID 집합
        Set<Long> localPosIds = new HashSet<>(posIds);

        // 현재 지역 POS에만 연결된 메뉴만 필터링
        List<Long> filteredMenuIds = menuToPosIdsMap.entrySet().stream()
                .filter(entry -> {
                    List<Long> menuPosList = entry.getValue();
                    // 메뉴가 연결된 모든 POS가 localPosIds 안에만 존재하는지 확인
                    return localPosIds.containsAll(menuPosList);
                })
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        if (filteredMenuIds.isEmpty()) {
            return Collections.emptyList();
        }

        // 최종 메뉴 조회
        List<Menu> menus = menuRepository.findByMenuIdIn(filteredMenuIds);

        // MenuPos 중 첫 번째 POS 기준으로 LocalMenuDTO 생성
        return menus.stream()
                .map(menu -> {
                    List<Long> menuPosList = menuToPosIdsMap.get(menu.getMenuId());
                    Long firstPosId = menuPosList.get(0); // 아무 POS 하나
                    String location = posLocationMap.get(firstPosId);
                    return new LocalMenuDTO(menu, location);
                })
                .collect(Collectors.toList());
    }

    // 두 지점 간의 거리 계산 (Haversine 공식)
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS_KM * c;
    }
}