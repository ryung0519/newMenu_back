package com.iot7.repository;

import com.iot7.entity.Review;
import com.iot7.entity.ReviewId;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, ReviewId> {
    List<Review> findByMenu_MenuId(Long menuId, Sort sort); // ✅ 수정된 부분

    List<Review> findByUser_UserId(String user_userId, Sort createdAt);
}
