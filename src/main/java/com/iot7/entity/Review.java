package com.iot7.entity;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;


@Entity
@Table(name = "REVIEW")
@Getter @Setter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class Review {

    @EmbeddedId
    private ReviewId id;

    @MapsId("menuId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MENU_ID")
    private Menu menu;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(name = "REVIEW_CONTENT")
    private String reviewContent;

    @Column(name = "REVIEW_RATING")
    private Short reviewRating;

    @Column(name = "TASTE")
    private String taste;

    @Column(name = "AMOUNT")
    private String amount;

    @Column(name = "WOULD_VISIT_AGAIN")
    private String wouldVisitAgain;

    @Column(name = "IMAGE_URLS")
    private String imageUrlsJson; // CLOB에 저장할 JSON 문자열

    // 이미지 리스트 getter/setter (자동 변환)
    public List<String> getImageUrls() {
        if (imageUrlsJson == null || imageUrlsJson.isBlank()) return List.of();
        try {
            return new ObjectMapper().readValue(imageUrlsJson, new TypeReference<>() {});
        } catch (Exception e) {
            return List.of(); // 에러 시 빈 리스트 반환
        }
    }

    public void setImageUrls(List<String> urls) {
        try {
            this.imageUrlsJson = new ObjectMapper().writeValueAsString(urls);
        } catch (Exception e) {
            this.imageUrlsJson = "[]";
        }
    }
}
