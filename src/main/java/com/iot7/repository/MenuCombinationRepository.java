package com.iot7.repository;

import com.iot7.entity.MenuCombination;
import com.iot7.entity.MenuCombinationId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuCombinationRepository extends JpaRepository<MenuCombination, MenuCombinationId> {
    List<MenuCombination> findByMenu_MenuIdAndUser_UserId(Long menuId, String userId);
}
