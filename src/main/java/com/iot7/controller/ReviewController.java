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

    @PostMapping
    public ResponseEntity<ReviewResponseDTO> writeReview(@RequestBody ReviewRequestDTO dto) {
        return ResponseEntity.ok(reviewService.saveOrUpdateReview(dto));
    }

    @GetMapping("/menu/{menuId}")
    public ResponseEntity<List<ReviewResponseDTO>> getReviews(
            @PathVariable Long menuId,
            @RequestParam(defaultValue = "desc") String order) {
        return ResponseEntity.ok(reviewService.getReviewListByMenuId(menuId, order));
    }
}
