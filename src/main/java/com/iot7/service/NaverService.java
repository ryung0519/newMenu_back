package com.iot7.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iot7.dto.BlogDTO;
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


/*네이버 API 서비스 파일*/

@Service
@RequiredArgsConstructor // final이 붙은 필드를 자동으로 생성자에 넣어줌
public class NaverService {

    // 키값 주입
    @Value("${naver.api.client-id}") // 프로퍼티스에서 가져온 키값
    private String naverClientId;

    @Value("${naver.api.client-secret}") // 프로퍼티스에서 가져온 값
    private String naverClientSecret;


    // ✅네이버 게시글 검색 함수
    public List<BlogDTO> fetchBlogPosts(String query) throws Exception {

        // ✅ 설정값을 주입받은 필드를, 메서드 안에서 편하게 쓰기 위해 한번 더 잡아줌
        String clientId = naverClientId;
        String clientSecret = naverClientSecret;

        // ✅ 검색할 주소 생성 query=검색어,sortdate=최신순,display=5개만 보여주기
        String apiURL = "https://openapi.naver.com/v1/search/blog?query=" +
                URLEncoder.encode(query, "UTF-8") + "&sort=date&display=15";

        // ✅ 네이버 api 서버에 요청보내기
        HttpURLConnection con = (HttpURLConnection) new URL(apiURL).openConnection();

        // ✅ 내 인증정보 알려주기
        con.setRequestMethod("GET");
        con.setRequestProperty("X-Naver-Client-Id", clientId);
        con.setRequestProperty("X-Naver-Client-Secret", clientSecret);

        // 요청 결과 200이면 성공, 아니면 error
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

        // ✅ 문자열을 객체로 바꾸기 - 글목록은 items 배열 안에 있음
        ObjectMapper mapper = new ObjectMapper();
        JsonNode items = mapper.readTree(response.toString()).get("items");



        // ✅ 블로그 글 하나씩 꺼내서 BlogPostDTO에 주입
        List<BlogDTO> posts = new ArrayList<>();
        for (JsonNode item : items) {
            BlogDTO post = new BlogDTO();
            post.setTitle(item.get("title").asText().replaceAll("<[^>]*>", "")); // ✅ 태그 제거
            post.setLink(item.get("link").asText());
            post.setBloggerName(item.get("bloggername").asText());
            post.setPostDate(item.get("postdate").asText());
            posts.add(post);
        }

        return posts; // List<BlogPostDTO> 리턴 -  fetchBlogPosts 끝
    }
}
