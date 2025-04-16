package com.iot7.service;

import com.iot7.dto.ReviewRequestDTO;
import com.iot7.dto.ReviewResponseDTO;
import com.iot7.entity.*;
import com.iot7.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    // 리뷰 저장 또는 수정
    public ReviewResponseDTO saveOrUpdateReview(ReviewRequestDTO dto) {
        ReviewId id = new ReviewId(dto.getMenuId(), dto.getUserId());
        Review review = Review.builder()
                .id(id)
                .menu(Menu.builder().menuId(dto.getMenuId()).build())
                .user(User.builder().userId(dto.getUserId()).build())
                .reviewContent(dto.getReviewContent())
                .reviewRating(dto.getReviewRating() != null ? dto.getReviewRating().shortValue() : null)
                .build();
        return new ReviewResponseDTO(reviewRepository.save(review));
    }

    // 메뉴 ID로 리뷰 리스트 반환
    public List<ReviewResponseDTO> getReviewListByMenuId(Long menuId) {
        return reviewRepository.findByMenu_MenuId(menuId).stream()
                .map(ReviewResponseDTO::new)
                .collect(Collectors.toList());
    }
}