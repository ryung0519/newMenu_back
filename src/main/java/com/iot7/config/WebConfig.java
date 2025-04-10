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


        //프론트와 백엔드가 서로 다른 도메인/포트를 사용할 때,
        //통신을 허용해주는 설정(CORS 허용) 파일



        // 여기 등록된 주소들만 백엔드 API를 호출할 수 있음
        configuration.setAllowedOrigins(List.of(
                "http://localhost:19006",       // 엑스포 웹 주소
                "http://192.168.0.124:8081",    // 예전 설정
                "http://192.168.0.124:19000",   // 모바일 Expo 앱 주소
                "http://172.20.10.3:19000",  // ← 엑스포 앱이 실행되는 주소
                "http://localhost:19006",      // ← 웹용 엑스포 실행 시 (선택)
                "http://10.20.64.9:8081" //내 컴퓨터 아이피주소 추가하기!


        ));

        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);  // JWT나 세션 쿠키 사용하는 경우

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
