package com.iot7.service;

import com.iot7.dto.PosDTO;
import com.iot7.entity.Pos;
import com.iot7.repository.MenuRepository;
import com.iot7.repository.PosRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PosService {

    private final PosRepository posRepository;

    // ✅ 요리 재료 갖다놓기 (생성자 주입)
    public PosService(PosRepository posRepository) {
        this.posRepository = posRepository;
    }

    // ✅ 사용자 위치와 브랜드명 기반으로 가까운 매장 5개 반환
    public List<PosDTO> findNearestStoresByBrand(String brandName, double userLat, double userLng) {

        // 1️⃣ 해당 브랜드의 POS 목록을 DB에서 바로 가져옴
        List<Pos> brandPosList = posRepository.findByBusinessUser_BusinessNameContainingIgnoreCase(brandName).stream()

                // 2️⃣ 좌표값이 없는 POS는 제외 (null 체크)
                .filter(pos -> pos.getLatitude() != null && pos.getLongitude() != null)

                // 3️⃣ 사용자 위치 기준으로 가까운 순 정렬
                .sorted(Comparator.comparingDouble(pos ->
                        calculateDistance(userLat, userLng, pos.getLatitude(), pos.getLongitude())))

                // 4️⃣ 가까운 5개만 선택
                .limit(3)

                // 5️⃣ 리스트로 수집
                .collect(Collectors.toList());

        // 6️⃣ POS 엔티티 → DTO로 변환
        // DB에서 GET해서 정보 가져오고, DTO로 변환해서 RETURN
        return brandPosList.stream().map(pos -> {
            PosDTO dto = new PosDTO();
            dto.setLocation(pos.getLocation()); // 매장 위치 정보 가져옴!!
            dto.setLatitude(pos.getLatitude()); // 위도
            dto.setLongitude(pos.getLongitude()); // 경도
            dto.setBusinessName(pos.getBusinessUser().getBusinessName()); // 브랜드 이름 가져옴!!
            return dto;
        }).collect(Collectors.toList());
    }

    // ✅ 거리 계산 메서드 (하버사인 공식)
    public double calculateDistance(double lat1, double lng1, double lat2, double lng2) {
        final int R = 6371; // 지구 반지름 (km)
        double dLat = Math.toRadians(lat2 - lat1); // 위도 차이
        double dLng = Math.toRadians(lng2 - lng1); // 경도 차이

        // 하버사인 공식
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLng / 2) * Math.sin(dLng / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;


    }
}




