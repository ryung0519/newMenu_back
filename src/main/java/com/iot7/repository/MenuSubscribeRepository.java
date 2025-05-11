package com.iot7.repository;

import com.iot7.entity.MenuSubscribe;
import com.iot7.entity.MenuSubscribeId;
import org.springframework.data.jpa.repository.JpaRepository;

// ✅ 복합키 MenuSubscribeId를 기반했음
// JPA가 기본적인 CRUD 메서드 자동 제공
// save(), deleteById(), existsById() 같은 기능 다 제공됨
public interface MenuSubscribeRepository extends JpaRepository<MenuSubscribe, MenuSubscribeId> {


    // ✅  해당 메뉴를 구독 중인지 여부 확인
    boolean existsById(MenuSubscribeId id);

    // ✅ 메뉴 구독 취소 (삭제는 간단하니 repository에서 하고, insert는 단계가 많으니 service에서 처리)
    void deleteById(MenuSubscribeId id);
}
