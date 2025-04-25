package com.iot7.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iot7.dto.YoutubeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/*유튜브 API 서비스 파일*/

@Service
@RequiredArgsConstructor // final이 붙은 필드를 자동으로 생성자에 넣어줌
public class YoutubeService {

    // 키값 주입
    @Value("${youtube.api.key}")
    private String youtubeApiKey;



    // ✅유튜브 영상 검색 함수
    public List<YoutubeDTO> fetchYoutubeVideos(String query) {
        try {
            String apiURL = "https://www.googleapis.com/youtube/v3/search?part=snippet" +
                    "&q=" + URLEncoder.encode(query + " 리뷰 후기", "UTF-8") +
                    "&type=video" +
                    "&order=relevance" +
                    "&maxResults=15" +
                    "&key=" + youtubeApiKey;

            HttpURLConnection con = (HttpURLConnection) new URL(apiURL).openConnection();
            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    responseCode == 200 ? con.getInputStream() : con.getErrorStream()
            ));

            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            br.close();

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.toString());

            // ❗ 오류 정보 로그 출력
            if (root.has("error")) {
                JsonNode error = root.get("error");
                String message = error.has("message") ? error.get("message").asText() : "Unknown error";
                String reason = error.path("errors").isArray() && error.path("errors").size() > 0
                        ? error.path("errors").get(0).path("reason").asText()
                        : "Unknown reason";

                System.out.println("\n========================= [YouTube API 오류] =========================");
                System.out.println("🔴 reason  : " + reason);
                System.out.println("🔴 message : " + message);
                System.out.println("=====================================================================\n");
                return Collections.emptyList();
            }

            JsonNode items = root.get("items");
            List<YoutubeDTO> videos = new ArrayList<>();
            if (items != null && items.isArray()) {
                for (JsonNode item : items) {
                    JsonNode idNode = item.get("id");
                    JsonNode snippetNode = item.get("snippet");
                    if (idNode != null && idNode.has("videoId") &&
                            snippetNode != null && snippetNode.has("title") &&
                            snippetNode.has("thumbnails") && snippetNode.get("thumbnails").has("high")) {

                        String videoId = idNode.get("videoId").asText();
                        String title = snippetNode.get("title").asText();
                        String thumbnail = snippetNode.get("thumbnails").get("high").get("url").asText();

                        videos.add(new YoutubeDTO(videoId, title, thumbnail));
                    }
                }
            }

            return videos;
        } catch (Exception e) {
            System.out.println("\n====================== [YouTube API 예외 발생] ======================");
            System.out.println("❗ 예외 메시지: " + e.getMessage());
            System.out.println("=====================================================================\n");
            return Collections.emptyList();
        }
    }





}

