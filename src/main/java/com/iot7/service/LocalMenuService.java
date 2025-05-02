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
import java.util.ArrayList;
import java.util.Arrays;

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

    // 특정 도시(keyword)에서만 판매되는 메뉴 목록을 찾아서 리턴
    public List<LocalMenuDTO> findMenusOnlyInLocation(String keyword) {
        //1. 해당 도시의 POS(매장) 정보 가져오기
        List<Pos> targetPositions = posRepository.findByLocationContaining(keyword);
        if (targetPositions.isEmpty()) return Collections.emptyList();

        //2. 가져온 POS들의 ID를 리스트로 변환
        List<Long> targetPosIds = targetPositions.stream()
                .map(Pos::getPosId)
                .collect(Collectors.toList());

        //3. 전체 메뉴-포스 관계 중에서 판매 중인 것만 뽑아옴
        List<MenuPos> allMenuPos = menuPosRepository.findAll().stream()
                .filter(mp -> mp.getSaleStatus() != null && mp.getSaleStatus() == 1)
                .collect(Collectors.toList());

        //4. 각 POS가 어떤 도시(시)에 있는지 매핑 ("충청남도 아산시 탕정면" → "아산시" 로 변환)
        Map<Long, String> posIdToCity = posRepository.findAll().stream()
                .filter(p -> p.getLocation() != null)
                .collect(Collectors.toMap(
                        Pos::getPosId,
                        p -> extractCityFromLocation(p.getLocation()),
                        (existing, replacement) -> existing));

        //5. 메뉴별로 팔리는 도시 리스트 만듦
        Map<Long, Set<String>> menuIdToCities = allMenuPos.stream()
                .collect(Collectors.groupingBy(MenuPos::getMenuId,
                        Collectors.mapping(mp -> posIdToCity.getOrDefault(mp.getPosId(), ""), Collectors.toSet())));

        //6. 해당 메뉴가 오직 하나의 도시에서만 팔리고, 그 도시가 keyword인 경우만 필터링
        Set<Long> onlyLocalMenuIds = menuIdToCities.entrySet().stream()
                .filter(entry -> entry.getValue().size() == 1 && entry.getValue().contains(keyword))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());

        if (onlyLocalMenuIds.isEmpty()) return Collections.emptyList();

        //7. 메뉴 ID로 실제 메뉴 데이터 가져오기
        List<Menu> menus = menuRepository.findByMenuIdIn(new ArrayList<>(onlyLocalMenuIds));

        //8. POS_ID → 전체 LOCATION(주소) 매핑
        Map<Long, String> posIdToLocation = targetPositions.stream()
                .collect(Collectors.toMap(Pos::getPosId, Pos::getLocation));

        //9. 메뉴별로 대표 POS 하나를 선택
        Map<Long, Long> menuToFirstPos = allMenuPos.stream()
                .filter(mp -> onlyLocalMenuIds.contains(mp.getMenuId()) && targetPosIds.contains(mp.getPosId()))
                .collect(Collectors.toMap(MenuPos::getMenuId, MenuPos::getPosId, (a, b) -> a));

        //10. DTO 변환해서 리턴
        return menus.stream()
                .map(menu -> new LocalMenuDTO(menu, posIdToLocation.getOrDefault(menuToFirstPos.get(menu.getMenuId()), "")))
                .collect(Collectors.toList());
    }
    private String extractCityFromLocation(String location) {
        return Arrays.stream(location.split(" "))
                .filter(token -> token.endsWith("시"))
                .findFirst()
                .orElse("");
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