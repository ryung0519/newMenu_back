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
        //통신을 허용해주는 CORS 파일
        //문열어주는 파일

      
        // React Native 혹은 웹 클라이언트 주소 허용
        configuration.setAllowedOrigins(List.of(
               //"http://192.168.0.124:8081",  // 192.x.x로 시작하는건 공유기(집, 사무실 등) 주소

               "http://192.168.0.35:8081" // 10.x.x.로 시작하는건 일부 공공망 주소

        ));

        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);  // JWT나 세션 쿠키 사용하는 경우

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
