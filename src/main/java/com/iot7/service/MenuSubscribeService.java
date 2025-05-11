package com.iot7.service;

import com.iot7.dto.MenuSubscribeDTO;
import com.iot7.entity.MenuSubscribe;
import com.iot7.entity.MenuSubscribeId;
import com.iot7.repository.MenuSubscribeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MenuSubscribeService {

    @Autowired
    private MenuSubscribeRepository repository;


    //메뉴 구독 메서드 (구독 or 취소)
    @Transactional // DB에 insert and delete가 일어나기 때문에 트랜잭션 명시해줘야
    public boolean toggleSubscribe(MenuSubscribeDTO dto) {
        // 복합키 객체 만들기 - JPA에서는 두개를 하나로 묶은 객체 필요
        MenuSubscribeId id = new MenuSubscribeId();
        id.setMenuId(dto.getMenuId());
        id.setUserId(dto.getUserId());

        // 이미 구독 중이면 구독 취소
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return false; // 구독 취소 상태 리턴
        }

        // 구독하지 않은 상태면 구독 등록
        MenuSubscribe sub = new MenuSubscribe();//DB 삽입용 엔티티 객체 만들기
        sub.setId(id); // 복합키
        sub.setRegDate(LocalDateTime.now());
        sub.setSubscribeStatus("Y"); // 상태는 기본값 "Y"

        repository.save(sub); // JPA가 insert 자동 처리
        return true;
    }

    // 특정 유저가 특정 메뉴를 구독중인지 여부 확인
    public boolean isSubscribed(Long userId, Long menuId) {
        MenuSubscribeId id = new MenuSubscribeId(); // 복합키 구성
        id.setUserId(userId);
        id.setMenuId(menuId);

        return repository.existsById(id); //DB에 있으면 true, 없으면 false
    }
}
