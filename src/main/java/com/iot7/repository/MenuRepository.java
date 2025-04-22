package com.iot7.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

import com.iot7.dto.MenuDTO;
import com.iot7.entity.Menu;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

    //카테고리 목록 중복없이 가져오기
    @Query("SELECT DISTINCT m.category FROM Menu m")
    List<String> findDistinctCategories();

    //메뉴목록을 DTO로 변환하여 가져오는 코드
    //(m.description,m.image, m.brand추가 필요)
    @Query("SELECT new com.iot7.dto.MenuDTO(m.menuId, m.menuName, m.category, m.price, m.businessUser.businessName, m.image, m.description, m.averageRating) FROM Menu m WHERE m.category = :category")
    List<MenuDTO> findMenusByCategory(@Param("category") String category);

    // ✅ 홈화면에서 검색창에 키워드 적어서 MENU_NAME 또는 INGREDIENTS에 포함된 메뉴 검색하는 것!
    List<Menu> findByMenuNameContainingIgnoreCaseOrIngredientsContainingIgnoreCase(String keyword1, String keyword2);

    // ✅BUSINESS_ID 하나로 메뉴들 조회
    List<Menu> findByBusinessUser_BusinessId(Long businessId);
}



