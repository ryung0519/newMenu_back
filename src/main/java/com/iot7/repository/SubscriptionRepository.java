package com.iot7.repository;

import com.iot7.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    //JPA 상속받아서 기본 save, findAll같은 자동 메서드 사용할 수 있음!!
}
