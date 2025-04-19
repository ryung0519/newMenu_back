package com.iot7.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iot7.dto.YoutubeVideoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


/*유튜브 API 서비스 파일*/

@Service
@RequiredArgsConstructor // final이 붙은 필드를 자동으로 생성자에 넣어줌
public class YoutubeService {

    // 키값 주입
    @Value("${youtube.api.key}")
    private String youtubeApiKey;



    // ✅유튜브 영상 검색 함수
    public List<YoutubeVideoDTO> fetchYoutubeVideos(String query) throws Exception {
        String apiURL = "https://www.googleapis.com/youtube/v3/search?part=snippet" + // part=snippet 영상 기본 정보 포함
                "&q=" + URLEncoder.encode(query + " 리뷰 후기", "UTF-8") +
                "&type=video" +
                "&order=relevance" + // ✅ 관련성 높은 순으로 정렬
                "&maxResults=15" + // ✅ 최대 결과수
                "&key=" + youtubeApiKey;

        // ✅ 네이버 api 서버에 요청보내기
        HttpURLConnection con = (HttpURLConnection) new URL(apiURL).openConnection();
        con.setRequestMethod("GET");

        // ✅ 200이면 성공, 아니면 에러
        int responseCode = con.getResponseCode();
        BufferedReader br = new BufferedReader(new InputStreamReader(
                responseCode == 200 ? con.getInputStream() : con.getErrorStream()
        ));

        // ✅ JSON 문자열로 만들기
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            response.append(line);
        }
        br.close();

        // ✅ 문자열을 객체로 바꾸기
        ObjectMapper mapper = new ObjectMapper();
        JsonNode items = mapper.readTree(response.toString()).get("items");

        // ✅ 블로그 글 하나씩 꺼내서 BlogPostDTO에 주입
        List<YoutubeVideoDTO> videos = new ArrayList<>();
        for (JsonNode item : items) {
            String videoId = item.get("id").get("videoId").asText();
            String title = item.get("snippet").get("title").asText();
            String thumbnail = item.get("snippet").get("thumbnails").get("high").get("url").asText();

            videos.add(new YoutubeVideoDTO(videoId, title, thumbnail));
        }

        return videos;
    }

}

