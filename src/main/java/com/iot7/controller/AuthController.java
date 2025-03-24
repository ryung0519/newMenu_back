package com.iot7.controller;

import com.iot7.dto.UserSignupRequest;
import com.iot7.entity.User;
import com.iot7.service.AuthService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

// 🔹 회원가입 API > 콘솔찍어서 확인해보기! > 안나올경우 안나오는 코드이다!
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserSignupRequest request) {
        System.out.println("✅ [백엔드] 회원가입 API 호출됨!"); // ← 로그 찍기
        try {
            User user = authService.registerUser(request); // uid 기반으로 저장
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("회원가입 실패: " + e.getMessage());
        }
    }

    // 🔹 로그인 API > 수정해야함
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody String token) {
        try {
            User user = authService.authenticateUser(token);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("로그인 실패: " + e.getMessage());
        }
    }
}
