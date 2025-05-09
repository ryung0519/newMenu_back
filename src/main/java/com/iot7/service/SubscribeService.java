package com.iot7.service;


import com.iot7.dto.SubscribeBrandDTO;
import com.iot7.dto.SubscribeDTO;
import com.iot7.entity.Subscription;
import com.iot7.repository.SubscriptionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscribeService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    // ✅ DTO를 받아서 Subscription 엔티티로 변환하고 DB에 저장
    public void subscribe(SubscribeDTO dto) {
        System.out.println("✅ [서버] 받은 userId: " + dto.getUserId());
        System.out.println("✅ [서버] 받은 businessId: " + dto.getBusinessId());

        // 구독 메서드
        // boolean 버전 호환안되어서 비슷한 int로 대체
        int count = subscriptionRepository.countByUserIdAndBusinessId(
                dto.getUserId(), dto.getBusinessId()
        );

        if (count > 0) {
            System.out.println("⚠️ 이미 구독한 사용자입니다.");
            return;
        }
        Subscription subscription = new Subscription();
        subscription.setUserId(dto.getUserId());
        subscription.setBusinessId(dto.getBusinessId());
        subscriptionRepository.save(subscription);
    }

    //✅ 구독하기 & 취소하기 메서드
    @Transactional // DB 수정 (등록/삭제) 니까 트랜젝션 필요
    public boolean subscribeOrToggle(SubscribeDTO dto) {
        System.out.println("✅ [서버] 받은 userId: " + dto.getUserId());
        System.out.println("✅ [서버] 받은 businessId: " + dto.getBusinessId());

        int count = subscriptionRepository.countByUserIdAndBusinessId(dto.getUserId(), dto.getBusinessId());

        if (count > 0) {
            // 이미 구독한 상태면 ➔ 구독 취소
            System.out.println("⚠️ 이미 구독한 사용자입니다. ➔ 구독 취소 진행!");
            subscriptionRepository.deleteByUserIdAndBusinessId(dto.getUserId(), dto.getBusinessId());
            System.out.println("✅ 구독 취소 완료!");
            return false; // ❌ 더 이상 구독 중이 아님
        } else {
            // 구독 안 한 상태면 ➔ 구독 등록
            Subscription subscription = new Subscription();
            subscription.setUserId(dto.getUserId());
            subscription.setBusinessId(dto.getBusinessId());
            subscriptionRepository.save(subscription);
            System.out.println("✅ 구독 등록 완료!");
            return true; // ✅ 지금 구독 중 상태
        }

    }
    //✅ 구독 여부 확인 (프론트에서 브랜드 진입 시 하트 상태용)
    public boolean isSubscribed(Long userId, Long businessId) {
        int count = subscriptionRepository.countByUserIdAndBusinessId(userId, businessId);
        return count > 0; // 하나라도 있으면 true
    }
    public List<SubscribeBrandDTO> getSubscribedBrandList(Long userId) {
        return subscriptionRepository.findSubscribedBrandsByUserId(userId);
    }
}