package com.iot7.controller;

import com.iot7.dto.PushTokenRequest;
import com.iot7.entity.User;
import com.iot7.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor // 생성자 자동으로 주입해줌
@RequestMapping("/api/push-token")
public class PushTokenController {

    private final UserRepository userRepository;

    // ✅ 푸시 토큰 저장 API (프론트에서 토큰 발급 후 이 API로 전송)
    @PostMapping
    public ResponseEntity<String> savePushToken(@RequestBody PushTokenRequest request) {
        // userId로 DB에서 유저 조회
        Optional<User> optionalUser = userRepository.findById(request.getUserId());

            // 유저가 존재할 경우
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            // ✅ 같은 푸시 토큰을 가진 다른 유저 찾아서 null 처리 (휴대폰 하나로 여러 계정 만들었을경우, 알림이 계정수만큼 오는 거 방지하기 위해)
            List<User> sameTokenUsers = userRepository.findByPushToken(request.getPushToken());
            for (User u : sameTokenUsers) {
                if (!u.getUserId().equals(user.getUserId())) {
                    u.setPushToken(null);
                    userRepository.save(u);
                }
            }

            // 현재 유저에게만 토큰 저장
            user.setPushToken(request.getPushToken());
            userRepository.save(user);

            return ResponseEntity.ok("푸시 토큰 저장 완료!");
        } else {
            return ResponseEntity.badRequest().body("존재하지 않는 사용자입니다.");
        }
    }
}
