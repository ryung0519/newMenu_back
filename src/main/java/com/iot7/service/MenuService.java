package com.iot7.service;
import org.springframework.beans.factory.annotation.Value;
import com.iot7.dto.YoutubeVideoDTO;
import org.springframework.beans.factory.annotation.Value;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iot7.dto.BlogPostDTO;
import com.iot7.dto.CalendarMenuDTO;
import com.iot7.dto.MenuDTO;
import com.iot7.dto.ProductDetailDTO;
import com.iot7.entity.Menu;
import com.iot7.repository.BusinessUserRepository;
import com.iot7.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList; // ⭐ 추가
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final BusinessUserRepository businessUserRepository;

    @Value("${youtube.api.key}") // properties에 있는 유튜브 키값을 변수에 자동 주입
    private String youtubeApiKey;

    @Value("${naver.api.client-id}") // properties에 있는 네이버 값을 변수에 자동 주입
    private String naverClientId;

    @Value("${naver.api.client-secret}")
    private String naverClientSecret;

    // 🔹 카테고리 목록 가져오기
    public List<String> getCategories() {
        return menuRepository.findDistinctCategories();
    }

    // 🔹 선택한 카테고리의 메뉴 목록 가져오기 (DTO 방식 반환)
    public List<MenuDTO> getMenuByCategory(String category) {
        List<MenuDTO> menus = menuRepository.findMenusByCategory(category); // ✅ 바로 DTO로 받기

        if (menus == null || menus.isEmpty()) {
            System.out.println(" 해당 카테고리에 대한 메뉴가 없습니다. " + category);
            return List.of();
        }

        return menus; // ✅ 그대로 반환
    }

    // 🔹 홈에서 키워드로 메뉴 검색
    public List<Menu> searchMenus(String keyword) {
        return menuRepository.findByMenuNameContainingIgnoreCaseOrIngredientsContainingIgnoreCase(keyword, keyword);
    }

    // 🔹 브랜드 필터링 - 본점 기반 메뉴 가져오기
    public List<MenuDTO> getMenusByBrandMainBranch(String brandName) {
        Long businessId = businessUserRepository.findMainBusinessIdByName(brandName)  // 본점 브랜드의 businiess_id를 가져옴
                .orElseThrow(() -> new RuntimeException("본점 정보 없음"));

        return menuRepository.findByBusinessUser_BusinessId(businessId)  // 그 아이디로 menu 조회한뒤,
                .stream()                                               // MENUDTO로 변환한뒤 리스트로 반환뒤 리턴
                .map(MenuDTO::fromEntity)
                .collect(Collectors.toList());
    }

    // 🔹 캘린더용 전체 메뉴 데이터 반환
    public List<CalendarMenuDTO> getAllMenuForCalendar() {
        return menuRepository.findAll().stream()
                .map(menu -> new CalendarMenuDTO(
                        menu.getMenuName(),
                        menu.getCategory(),
                        menu.getRegDate(),
                        menu.getBrand(),
                        menu.getDescription(),
                        menu.getPrice(),
                        menu.getImage()
                ))
                .collect(Collectors.toList());
    }


    // 🔹 메뉴 ID로 상세 정보 조회 (상세 페이지용)
    public ProductDetailDTO getProductDetailById(Long menuId) {
        Menu menu = menuRepository.findById(menuId) // 1) 메뉴 정보 db에서 가져옴
                .orElseThrow(() -> new RuntimeException("해당 메뉴를 찾을 수 없습니다."));


        /*menu 에 있는 것들을 바탕으로 new dto 를 만들어서, 거기에 블로그 글도 추가하는 흐름*/

        ProductDetailDTO dto = new ProductDetailDTO().convertToProductDetailDTO(menu); // 2) 메뉴 정보를 기반으로 새로운 dto 만들기
                                                                                        // convert 포장용지

        // ✅ 블로그 + 유튜브 API에 검색어 보내기 위한 쿼리 - 브랜드명 + 메뉴명
        String query = menu.getBusinessUser().getBusinessName() + " " + menu.getMenuName();

        try {
            List<BlogPostDTO> blogPosts = fetchBlogPosts(query); // 블로그 글 검색 요청
            dto.setBlogPosts(blogPosts); // dto에 블로그 글까지 추가로 담기
        } catch (Exception e) {
            e.printStackTrace();
            dto.setBlogPosts(new ArrayList<>()); // 실패 시 빈 리스트 반환
        }

        try {
            List<YoutubeVideoDTO> youtubeVideos = fetchYoutubeVideos(query); // 유튜브 영상 조회
            dto.setYoutubeVideos(youtubeVideos);
        } catch (Exception e) {
            e.printStackTrace();
            dto.setYoutubeVideos(new ArrayList<>()); // 실패 시 빈값
        }


        return dto;

    }

    {/*네이버 + 유튜브 불러오는 함수 코드들*/}


    // ✅ 1.네이버 게시글 검색 함수 추가
    private List<BlogPostDTO> fetchBlogPosts(String query) throws Exception {

        // ✅ @Value로 주입한 설정값 사용
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
        List<BlogPostDTO> posts = new ArrayList<>();
        for (JsonNode item : items) {
            BlogPostDTO post = new BlogPostDTO();
            post.setTitle(item.get("title").asText().replaceAll("<[^>]*>", "")); // ✅ 태그 제거
            post.setLink(item.get("link").asText());
            post.setBloggerName(item.get("bloggername").asText());
            post.setPostDate(item.get("postdate").asText());
            posts.add(post);
        }

        return posts; // List<BlogPostDTO> 리턴 -  fetchBlogPosts 끝
    }



    // ✅ 2. 유튜브 영상 검색 함수 추가
    private List<YoutubeVideoDTO> fetchYoutubeVideos(String query) throws Exception {
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
