package com.iot7.controller;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    // ✅ 환경 변수에서 Firebase 설정값 불러오기
    @Value("${google.client.id}")
    private String googleClientId;

    @Value("${google.client.secret}")
    private String googleClientSecret;

    @PostMapping("/google")
    public ResponseEntity<Map<String, String>> authenticateWithGoogle(@RequestBody Map<String, Object> requestBody) {
        Map<String, String> response = new HashMap<>();

        try {


            // 요청 값이 비어있거나 idToken 이 없을 경우, 404 에러 반환하기!
            if (requestBody == null || !requestBody.containsKey("idToken")) {
                response.put("error", "ID 토큰이 전달되지 않았습니다.");
                return ResponseEntity.badRequest().body(response);
            }

            //idToken 이라는 이름의 데이터를 꺼내 문자열로 반환하기!
            String idToken = requestBody.get("idToken").toString();
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);


            //토큰에서 사용자 고유 ID와 이메일 정보를 가져오기!
            String uid = decodedToken.getUid();
            String email = decodedToken.getEmail();

            response.put("message", "Google 로그인 성공");
            response.put("uid", uid);
            response.put("email", email);

            //로그인 한다음, 첫화면 url함께 지정할코드, 리엑트 연동후 그 형태로 다시 작성
            /*response.put("redirectUrl", "/home");*/

            return ResponseEntity.ok(response);

        } catch (FirebaseAuthException e) {
            response.put("error", "Google 로그인 실패: " + e.getMessage());
            return ResponseEntity.status(401).body(response);
        } catch (Exception e) {
            System.out.println("❌ 예외 발생: " + e.getMessage()); // 예외 로그 확인
            response.put("error", "서버 오류: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
}
