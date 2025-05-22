package com.iot7.service;

import com.iot7.entity.Menu;
import com.iot7.repository.MenuRepository;
import com.iot7.repository.PushTokenRepository;
import com.iot7.repository.SubscriptionRepository;
import com.iot7.repository.UserRepository;
import com.iot7.util.FirebasePushUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PushTokenService {

    private final MenuRepository menuRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final PushTokenRepository pushTokenRepository;
    private final FirebasePushUtil firebasePushUtil;

    // ✅ 메뉴 저장 + 구독자에게 푸시 알림 전송
    public void saveMenuAndNotify(Menu menu) {
        System.out.println("🔔 알림 서비스 실행됨: " + menu.getMenuName());
        // 1. 메뉴 저장
        menuRepository.save(menu);


        // 2. 메뉴에 연결된 브랜드 ID
        Long businessId = menu.getBusinessUser().getBusinessId();
        System.out.println("📦 브랜드 ID: " + businessId);


        // 3. 이 브랜드를 구독 중인 사용자 ID 목록 조회
        List<Long> userIds = subscriptionRepository.findUserIdsByBusinessId(businessId);
        System.out.println("👥 구독자 수: " + userIds.size());

        // 4. 각 유저에게 푸시 알림 전송
        for (Long userId : userIds) {
            String token = pushTokenRepository.findPushTokenByUserId(userId.toString()); // ✅ String으로 변환
            System.out.println("➡️ userId: " + userId + ", token: " + token);
            if (token != null) {
                firebasePushUtil.sendNotification(
                        token,
                        "📢 신메뉴 알림!",
                        menu.getMenuName() + "가 출시되었어요!"
                );
            }
        }
    }
}