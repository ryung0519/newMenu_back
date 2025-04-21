package com.iot7.service;

import com.iot7.dto.ReviewRequestDTO;
import com.iot7.dto.ReviewResponseDTO;
import com.iot7.entity.Menu;
import com.iot7.entity.Review;
import com.iot7.entity.ReviewId;
import com.iot7.entity.User;
import com.iot7.repository.ReviewRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @PersistenceContext
    private EntityManager entityManager;

    //엔티티 > dto 변환
    public ReviewResponseDTO saveOrUpdateReview(ReviewRequestDTO dto) {
        if (dto.getMenuId() == null || dto.getUserId() == null) {
            throw new IllegalArgumentException("menuId와 userId는 null일 수 없습니다.");
        }

        // proxy 객체를 가져오되, ID가 null이면 예외 발생 → 방지됨
        Menu menu = entityManager.find(Menu.class, dto.getMenuId());
        User user = entityManager.find(User.class, dto.getUserId());

        if (menu == null) {
            throw new IllegalArgumentException("해당 menuId에 해당하는 메뉴가 존재하지 않습니다.");
        }
        if (user == null) {
            throw new IllegalArgumentException("해당 userId에 해당하는 유저가 존재하지 않습니다.");
        }

        Review review = Review.builder()
                .id(new ReviewId(dto.getMenuId(), dto.getUserId()))
                .menu(menu)
                .user(user)
                .reviewContent(dto.getReviewContent())
                .reviewRating(dto.getReviewRating().shortValue())
                .taste(dto.getTaste())
                .amount(dto.getAmount())
                .wouldVisitAgain(dto.getWouldVisitAgain())
                .build();

        review.setImageUrls(dto.getImageUrls()); // JSON 직렬화하여 저장

        return new ReviewResponseDTO(reviewRepository.save(review));
    }

    public List<ReviewResponseDTO> getReviewListByMenuId(Long menuId) {
        return reviewRepository.findByMenu_MenuId(menuId).stream()
                .map(ReviewResponseDTO::new)
                .collect(Collectors.toList());
    }
}
