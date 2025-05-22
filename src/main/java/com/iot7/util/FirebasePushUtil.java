// ✅ FirebasePushUtil.java
// 실제로 Expo 서버를 통해 푸시 알림을 전송하는 유틸 클래스

package com.iot7.util;

import com.iot7.entity.User;
import com.iot7.repository.PushTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FirebasePushUtil {

    private final PushTokenRepository pushTokenRepository; // ✅ 의존성 주입 (반드시 필요)


    // ✅ 푸시 알림을 전송하는 메서드
    public void sendNotification(String token, String title, String body) {
        try {
            HttpClient client = HttpClient.newHttpClient();


            // ✅ expo push 알림 전송을 위한 요청 생성
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://exp.host/--/api/v2/push/send"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString("""
                        {
                          "to": "%s",
                          "sound": "default",
                          "title": "%s",
                          "body": "%s"
                        }
                    """.formatted(token, title, body)))
                    .build();

            // ✅ 서버에 요청 전송
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();

            System.out.println("[푸시알림 응답] " + response.body());

            // ✅ 응답에 DeviceNotRegistered가 포함되면 → DB에서 해당 토큰 제거
            if (responseBody.contains("DeviceNotRegistered")) {
                System.out.println("⚠️ 유효하지 않은 토큰 – DB에서 제거: " + token);

                List<User> users = pushTokenRepository.findByPushToken(token);
                for (User user : users) {
                    user.setPushToken(null);
                    pushTokenRepository.save(user);
                }
            }

        } catch (Exception e) {
            System.err.println("[푸시알림 실패]");
            e.printStackTrace();
        }
    }
}
