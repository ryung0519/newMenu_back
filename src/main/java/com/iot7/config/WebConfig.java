package com.iot7.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class WebConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // React Native 혹은 웹 클라이언트 주소 허용
        configuration.setAllowedOrigins(List.of(
                "http://localhost:19006",       // 엑스포 웹 주소
                "http://192.168.0.124:8081",    // 예전 설정
                "http://192.168.0.124:19000",   // 모바일 Expo 앱 주소
                "http://10.20.64.112:19000",  // ← 엑스포 앱이 실행되는 주소
                "http://localhost:19006",      // ← 웹용 엑스포 실행 시 (선택)
                "http://10.20.64.118:8081" //내 컴퓨터 아이피주소 추가하기!


        ));

        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);  // JWT나 세션 쿠키 사용하는 경우

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
