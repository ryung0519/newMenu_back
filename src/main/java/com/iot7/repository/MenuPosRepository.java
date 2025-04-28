package com.iot7.repository;

import com.iot7.entity.MenuPos;
import com.iot7.entity.MenuPosId;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MenuPosRepository extends JpaRepository<MenuPos, MenuPosId> {
    // POS ID와 판매 상태로 메뉴-POS 연결 정보 검색
    List<MenuPos> findByPosIdInAndSaleStatus(List<Long> posIds, Integer saleStatus);
}
