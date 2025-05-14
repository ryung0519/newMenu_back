package com.iot7.repository;


import com.iot7.entity.MenuClickLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuClickLogRepository extends JpaRepository<MenuClickLog, Long> {
} // Jpa에서 상속받았기 떄문에, save() 메서드 사용 가능
