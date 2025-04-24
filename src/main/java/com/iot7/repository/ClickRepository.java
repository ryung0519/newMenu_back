package com.iot7.repository;

import com.iot7.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClickRepository extends JpaRepository<Menu, Long> {

    @Query("SELECT m FROM Menu m WHERE m.businessUser.businessName = :brandName ORDER BY COALESCE(m.clickCount, 0) DESC")
    List<Menu> findPopularMenusByBrand(@Param("brandName") String brandName);
}

