package com.iot7.repository;


import com.iot7.entity.Pos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/*DB에서 POS(매장정보)를 받아오기 위해 만든 파일 */
@Repository
public interface PosRepository extends JpaRepository<Pos, Long> {

    // ✅ 브랜드 이름으로 POS가져오기
    List<Pos> findByBusinessUser_BusinessNameContainingIgnoreCase(String businessName);

    // 지역명으로 위치 검색 (지역기반 메뉴)
    List<Pos> findByLocationContaining(String locationKeyword);

    // 좌표로 위치 검색 (좌표 기반 검색 메소드) -->
    @Query("SELECT p FROM Pos p " +
            "WHERE p.latitude IS NOT NULL AND p.longitude IS NOT NULL " +
            "ORDER BY FUNCTION('calculate_distance', :latitude, :longitude, p.latitude, p.longitude)")
    List<Pos> findPosOrderByDistance(@Param("latitude") double latitude,
                                     @Param("longitude") double longitude);
}
