package com.iot7.repository;

import com.iot7.dto.HotMenuDTO;
import com.iot7.entity.Menu;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ClickRepository extends JpaRepository<Menu, Long> {


    //브랜드의 인기상품 click_count가 아니라 menu_click_log로 조회
    //최근 2주동안 해당 브랜드의 인기 제품
    @Query("SELECT m " +
            "FROM Menu m " +
            "JOIN (SELECT l.menuId as menuId, COUNT(l) as clickCount " +
            "      FROM MenuClickLog l " + // 메뉴 클릭 로그 테이블에서
            "      WHERE l.clickTime >= :startDate " +  // 2주전부터 오늘까지 날짜 계산
            "      GROUP BY l.menuId) log " +
            "ON m.menuId = log.menuId " +
            "WHERE m.businessUser.businessName = :brandName " +
            "ORDER BY log.clickCount DESC")
    List<Menu> findPopularMenusByBrand(@Param("brandName") String brandName,
                                       @Param("startDate") LocalDateTime startDate);


    //최근 1주동안 전체 브랜드의 핫한 제품 (Oracle 전용 SQL을 그대로 사용)
    @Query(value = """
            SELECT *
    FROM (
        SELECT m.menu_id AS menuId,
               m.menu_name AS menuName,
               m.image_url AS imageUrl,
               COUNT(*) AS clickCount
        FROM menu_click_log l
        JOIN menu m ON l.menu_id = m.menu_id
        WHERE l.click_time >= :startDate
        GROUP BY m.menu_id, m.menu_name, m.image_url
        ORDER BY COUNT(*) DESC
    )
    WHERE ROWNUM <= 7 // Oracle에서 안정적으로 결과 제한하는 방법 (상위 7개 제품 보여주기)
    """, nativeQuery = true)
    List<Object[]> findTop7HotMenus(@Param("startDate") LocalDateTime startDate);
}






