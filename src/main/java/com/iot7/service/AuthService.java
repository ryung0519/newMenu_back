package com.iot7.service;

import com.iot7.dto.UserSignupRequest;
import com.iot7.entity.User;
import com.iot7.repository.UserRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 🔹 회원가입
    public User registerUser(UserSignupRequest request) throws Exception {
        String uid = request.getUid();
        // 이메일 중복 체크
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            throw new Exception("이미 가입된 이메일입니다.");
        }
        // 유저 저장
        User newUser = new User();
        newUser.setUserId(uid); //아이디 주입
        newUser.setUserName(request.getName()); //이름 주입
        newUser.setEmail(request.getEmail()); //이메일 주입
        newUser.setPassword(request.getPassword()); // 비번 주입
        newUser.setPreferredFood(request.getPreferredFood());       //좋아하는 음식 주입
        newUser.setAllergicFood(request.getAllergicFood());      //싫어하는 음식 주입
        newUser.setRegDate(new Date()); // 가입날짜

        return userRepository.save(newUser); // 저장받기
    }

    // 로그인  (토큰 검증 후 유저 조회)
    public User authenticateUser(String token) throws Exception {
        //  Firebase 토큰 검증
        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
        String email = decodedToken.getEmail(); // 이메일 가져오기


        //  이메일로 DB 조회
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("해당 이메일의 유저를 찾을 수 없습니다."));
    }
}
