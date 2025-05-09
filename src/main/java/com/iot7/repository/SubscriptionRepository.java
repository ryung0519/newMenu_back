package com.iot7.repository;

import com.iot7.dto.SubscribeBrandDTO;
import com.iot7.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    //JPA 상속받아서 기본 save, findAll같은 자동 메서드 사용할 수 있음!!

    //구독 조회(count로 대체)
    int countByUserIdAndBusinessId(Long userId, Long businessId);

    // ✅ 구독 삭제
    void deleteByUserIdAndBusinessId(Long userId, Long businessId);

    // ✅ 특정 브랜드를 구독한 유저 ID 목록 가져오기
    @Query("SELECT s.userId FROM Subscription s WHERE s.businessId = :businessId")
    List<Long> findUserIdsByBusinessId(@Param("businessId") Long businessId);

    @Query("""
    SELECT new com.iot7.dto.SubscribeBrandDTO(b.businessId, b.businessName)
    FROM Subscription s
    JOIN BusinessUser b ON s.businessId = b.businessId
    WHERE s.userId = :userId
""")
    List<SubscribeBrandDTO> findSubscribedBrandsByUserId(@Param("userId") Long userId);
}


