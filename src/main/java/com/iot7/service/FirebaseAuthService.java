package com.iot7.service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import org.springframework.stereotype.Service;

import java.util.Map;


//Firebase에 사용자 등록과 로그인하는 파일
@Service
public class FirebaseAuthService {

    public String createFirebaseToken(Map<String, Object> kakaoUserInfo) {
        String uid = "kakao_" + kakaoUserInfo.get("id"); // Firebase 사용자 UID

        try {
            // Firebase에 사용자 추가 (이미 있으면 업데이트)
            UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                    .setUid(uid)
                    .setEmail((String) ((Map<String, Object>) kakaoUserInfo.get("kakao_account")).get("email"))
                    .setDisplayName((String) ((Map<String, Object>) kakaoUserInfo.get("properties")).get("nickname"));

            UserRecord userRecord;
            try {
                userRecord = FirebaseAuth.getInstance().getUser(uid);
            } catch (Exception e) {
                userRecord = FirebaseAuth.getInstance().createUser(request);
            }

            // Firebase Custom Token 생성
            return FirebaseAuth.getInstance().createCustomToken(uid);
        } catch (Exception e) {
            throw new RuntimeException("Firebase 인증 실패", e);
        }
    }
}
