package com.iot7.repository;

import com.iot7.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ClickRepository extends JpaRepository<Menu, Long> {


    //브랜드의 인기상품 조회 click_count가 아니라 menu_click_log로 조회
    //2주간 클릭수가 가장 많은 제품을 인기상품으로 보여줌
    @Query("SELECT m " +
            "FROM Menu m " +
            "JOIN (SELECT l.menuId as menuId, COUNT(l) as clickCount " +
            "      FROM MenuClickLog l " +
            "      WHERE l.clickTime >= :startDate " +  // 2주전부터 오늘까지 날짜 계산
            "      GROUP BY l.menuId) log " +
            "ON m.menuId = log.menuId " +
            "WHERE m.businessUser.businessName = :brandName " +
            "ORDER BY log.clickCount DESC")
    List<Menu> findPopularMenusByBrand(@Param("brandName") String brandName,
                                       @Param("startDate") LocalDateTime startDate);
}


