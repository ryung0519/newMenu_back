package com.iot7.controller;

import com.iot7.dto.ReviewRequestDTO;
import com.iot7.dto.ReviewResponseDTO;
import com.iot7.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    // 리뷰 작성 또는 수정
    @PostMapping
    public ResponseEntity<ReviewResponseDTO> writeReview(@RequestBody ReviewRequestDTO dto) {
        return ResponseEntity.ok(reviewService.saveOrUpdateReview(dto));
    }

    // 특정 메뉴의 리뷰 목록 조회
    @GetMapping("/menu/{menuId}")
    public ResponseEntity<List<ReviewResponseDTO>> getReviews(@PathVariable Long menuId) {
        return ResponseEntity.ok(reviewService.getReviewListByMenuId(menuId));
    }
}