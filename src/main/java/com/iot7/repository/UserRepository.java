package com.iot7.repository;

import com.iot7.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);


    // userId로 해당 유저의 pushToken 조회
    @Query("SELECT u.pushToken FROM User u WHERE u.userId = :userId")
    String findPushTokenByUserId(@Param("userId") Long userId);



}
