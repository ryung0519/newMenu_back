package com.iot7.repository;

import com.iot7.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


    //회원가입 & 로그인
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);




}
