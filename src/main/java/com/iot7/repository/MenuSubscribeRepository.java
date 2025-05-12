package com.iot7.repository;

import com.iot7.dto.SubscribedMenuDTO;
import com.iot7.entity.MenuSubscribe;
import com.iot7.entity.MenuSubscribeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import java.util.Optional;

// ✅ 복합키 MenuSubscribeId를 기반했음
// JPA가 기본적인 CRUD 메서드 자동 제공
// save(), deleteById(), existsById() 같은 기능 다 제공됨
public interface MenuSubscribeRepository extends JpaRepository<MenuSubscribe, MenuSubscribeId> {

    // ✅ userId + menuId로 구독 기록을 조회하고 subscribeStatus가 Y인지 N인지 확인
    Optional<MenuSubscribe> findById(MenuSubscribeId id);

  
}
