package com.iot7.service;

import com.iot7.entity.Menu;
import com.iot7.entity.User;
import com.iot7.repository.MenuRepository;
import com.iot7.repository.PushTokenRepository;
import com.iot7.repository.SubscriptionRepository;
import com.iot7.repository.UserRepository;
import com.iot7.util.FirebasePushUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PushTokenService {

    private final MenuRepository menuRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository; // ✅ 추가
    private final FirebasePushUtil firebasePushUtil;

    // ✅ 메뉴 저장 + 구독자에게 푸시 알림 전송
    public void saveMenuAndNotify(Menu menu) {
        System.out.println("🔔 알림 서비스 실행됨: " + menu.getMenuName());

        // 1. 메뉴 저장
        menuRepository.save(menu);

        // 2. 브랜드 ID 가져오기
        Long businessId = menu.getBusinessUser().getBusinessId();
        System.out.println("📦 브랜드 ID: " + businessId);

        // 3. 해당 브랜드를 구독한 사용자 ID 리스트 가져오기
        List<Long> userIds = subscriptionRepository.findUserIdsByBusinessId(businessId);
        System.out.println("👥 구독자 수: " + userIds.size());

        // 4. 각 사용자에게 푸시 발송 (조건: 알림 ON + 푸시 토큰 존재)
        for (Long userId : userIds) {
            Optional<User> optionalUser = userRepository.findById(userId.toString());

            if (optionalUser.isPresent()) {
                User user = optionalUser.get();

                if ("Y".equals(user.getNotificationYn()) && user.getPushToken() != null) {
                    firebasePushUtil.sendNotification(
                            user.getPushToken(),
                            "📢 신메뉴 알림!",
                            menu.getMenuName() + "가 출시되었어요!"
                    );
                    System.out.println("✅ 발송됨: " + user.getUserId());
                } else {
                    System.out.println("🚫 알림 OFF 또는 토큰 없음: " + user.getUserId());
                }
            }
        }
    }
}
