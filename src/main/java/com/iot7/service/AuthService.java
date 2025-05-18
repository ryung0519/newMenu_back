package com.iot7.service;

import com.iot7.dto.SignupDTO;
import com.iot7.entity.User;
import com.iot7.repository.UserRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 🔹 회원가입
    public User registerUser(SignupDTO request) throws Exception {
        String uid = request.getUid();
        // 이메일 중복 체크
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            throw new Exception("이미 가입된 이메일입니다.");
        }
        // UID (같은 계정인지) 중복 체크
        if (userRepository.existsById(uid)) {
            throw new Exception("이미 가입된 계정입니다. 로그인을 해주세요");
        }
        // db 저장용 User 엔티티 값에 넣기
        User newUser = new User();
        newUser.setUserId(uid); //아이디 주입
        newUser.setUserName(request.getName()); //이름 주입
        newUser.setEmail(request.getEmail()); //이메일 주입
        newUser.setPassword(passwordEncoder.encode(request.getPassword())); //비번 암호화로 저장
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

    public User updateUserProfile(String userId, String preferredFood, String allergicFood) throws Exception {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new Exception("유저를 찾을 수 없습니다.");
        }

        User user = userOptional.get();
        user.setPreferredFood(preferredFood);
        user.setAllergicFood(allergicFood);

        return userRepository.save(user);
    }

}