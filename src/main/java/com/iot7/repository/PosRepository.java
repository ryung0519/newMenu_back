package com.iot7.repository;


import com.iot7.entity.Pos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/*DB에서 POS(매장정보)를 받아오기 위해 만든 파일 */


@Repository
public interface PosRepository extends JpaRepository<Pos, Long> {

    // ✅ 브랜드 이름으로 POS가져오기
    List<Pos> findByBusinessUser_BusinessNameContainingIgnoreCase(String businessName);
}
