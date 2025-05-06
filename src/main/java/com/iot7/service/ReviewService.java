// ReviewService.java
package com.iot7.service;

import com.iot7.dto.ReviewRequestDTO;
import com.iot7.dto.ReviewResponseDTO;
import com.iot7.entity.*;
import com.iot7.repository.MenuCombinationRepository;
import com.iot7.repository.ReviewRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MenuCombinationRepository menuCombinationRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public ReviewResponseDTO saveOrUpdateReview(ReviewRequestDTO dto) {
        if (dto.getMenuId() == null || dto.getUserId() == null) {
            throw new IllegalArgumentException("menuId와 userId는 null이 아닙니다.");
        }

        Menu menu = entityManager.find(Menu.class, dto.getMenuId());
        User user = entityManager.find(User.class, dto.getUserId());

        if (menu == null) throw new IllegalArgumentException("해당 메뉴가 존재하지 않습니다.");
        if (user == null) throw new IllegalArgumentException("해당 유저가 존재하지 않습니다.");

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

        review.setImageUrls(dto.getImageUrls());

        if (dto.getPairedMenuId() != null && dto.getCombinationContent() != null && !dto.getCombinationContent().isBlank()) {
            Menu pairedMenu = entityManager.find(Menu.class, dto.getPairedMenuId());
            if (pairedMenu == null) {
                throw new IllegalArgumentException("pairedMenuId에 해당하는 메뉴가 존재하지 않습니다.");
            }

            MenuCombination combo = new MenuCombination();
            combo.setId(new MenuCombinationId(dto.getMenuId(), dto.getUserId(), dto.getPairedMenuId()));
            combo.setMenu(menu);
            combo.setUser(user);
            combo.setPairedMenu(pairedMenu);
            combo.setCombinationContent(dto.getCombinationContent());

            menuCombinationRepository.save(combo);
        }

        return new ReviewResponseDTO(reviewRepository.save(review));
    }

    public List<ReviewResponseDTO> getReviewListByMenuId(Long menuId, String order) {
        Sort sort = order.equalsIgnoreCase("asc")
                ? Sort.by("createdAt").ascending()
                : Sort.by("createdAt").descending();

        List<Review> reviewList = reviewRepository.findByMenu_MenuId(menuId, sort);

        return reviewList.stream()
                .map(review -> {
                    String pairedName = menuCombinationRepository
                            .findByMenu_MenuIdAndUser_UserId(menuId, review.getId().getUserId())
                            .stream()
                            .findFirst()
                            .map(combo -> combo.getPairedMenu().getMenuName())
                            .orElse(null);

                    return new ReviewResponseDTO(review, pairedName);
                })
                .collect(Collectors.toList());
    }
}