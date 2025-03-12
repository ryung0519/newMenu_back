package com.iot7.controller;

import com.iot7.service.FirebaseAuthService;
import com.iot7.service.KakaoAuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final KakaoAuthService kakaoAuthService;
    private final FirebaseAuthService firebaseAuthService;

    public AuthController(KakaoAuthService kakaoAuthService, FirebaseAuthService firebaseAuthService) {
        this.kakaoAuthService = kakaoAuthService;
        this.firebaseAuthService = firebaseAuthService;
    }

    @PostMapping("/kakao")
    public ResponseEntity<?> kakaoLogin(@RequestBody Map<String, String> request) {
        String kakaoAccessToken = request.get("accessToken");

        // 1. 카카오 API에서 사용자 정보 가져오기
        Map<String, Object> userInfo = kakaoAuthService.getUserInfo(kakaoAccessToken);

        // 2. Firebase 사용자 등록과 Token 생성하기~
        String firebaseToken = firebaseAuthService.createFirebaseToken(userInfo);

        // 3. Firebase Token 응답
        return ResponseEntity.ok().body(Map.of("firebaseToken", firebaseToken));
    }
}
