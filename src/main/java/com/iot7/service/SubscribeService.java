package com.iot7.service;


import com.iot7.dto.SubscribeDTO;
import com.iot7.entity.Subscription;
import com.iot7.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscribeService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    // DTO를 받아서 Subscription 엔티티로 변환하고 DB에 저장
    public void subscribe(SubscribeDTO dto) {
        Subscription subscription = new Subscription();
        subscription.setUserId(dto.getUserId());
        subscription.setBusinessId(dto.getBusinessId());
        subscriptionRepository.save(subscription);
    }
}