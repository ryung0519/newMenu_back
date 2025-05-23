package com.iot7.repository;

import com.iot7.dto.SubscribedMenuDTO;
import com.iot7.entity.MenuSubscribe;
import com.iot7.entity.MenuSubscribeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface MenuSubscribeRepository extends JpaRepository<MenuSubscribe, MenuSubscribeId> {

    // ✅ userId + menuId로 구독 기록을 조회하고 subscribeStatus가 Y인지 N인지 확인
    Optional<MenuSubscribe> findById(@NonNull MenuSubscribeId id);

    // ✅ 구독 중인 메뉴 목록을 DTO로 조회 (정상 쿼리)vvvvvvvvv
    @Query("SELECT new com.iot7.dto.SubscribedMenuDTO(" +
            "m.menuId, m.menuName, m.image, m.description, m.businessUser.businessName, m.price) " +
            "FROM MenuSubscribe ms " +
            "JOIN Menu m ON ms.id.menuId = m.menuId " +
            "WHERE ms.id.userId = :userId AND ms.subscribeStatus = 'Y'")
    List<SubscribedMenuDTO> findSubscribedMenusByUserId(@Param("userId") @NonNull Long userId);
}
