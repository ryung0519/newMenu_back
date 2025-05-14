package com.iot7.service;

import com.iot7.dto.MenuSubscribeDTO;
import com.iot7.dto.SubscribedMenuDTO;
import com.iot7.entity.MenuSubscribe;
import com.iot7.entity.MenuSubscribeId;
import com.iot7.repository.MenuSubscribeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;

import java.util.List;

@Service
public class MenuSubscribeService {

    @Autowired
    private MenuSubscribeRepository repository;


    // ✅ 1. 메뉴 구독 메서드 (구독 or 취소)
    @Transactional // DB에 insert and delete가 일어나기 때문에 트랜잭션 명시해줘야
    public boolean toggleSubscribe(MenuSubscribeDTO dto) {

        // 복합키 객체 만들기 - JPA에서는 두개를 하나로 묶은 객체 필요
        MenuSubscribeId id = new MenuSubscribeId();
        id.setMenuId(dto.getMenuId());
        id.setUserId(dto.getUserId());

        // 현재 구독 정보가 DB에 있는지 확인( 있으면 Optional에 담김)
        Optional<MenuSubscribe> optional = repository.findById(id);

        // 구독 이력 있는 경우
        if (optional.isPresent()) {
            MenuSubscribe sub = optional.get();

            // 현재 상태 Y > N 로 바꿔서 구독 해제
            if ("Y".equals(sub.getSubscribeStatus())) {
                sub.setSubscribeStatus("N"); // 구독해제
            } else {
                // 반대면 N > Y로 재구독
                sub.setSubscribeStatus("Y");
            }
            // 저장
            repository.save(sub);
            // 프론트 표시용 여부 리턴
            return "Y".equals(sub.getSubscribeStatus());
        } else {
            // 구독 이력이 없는 경우
            MenuSubscribe newSub = new MenuSubscribe();
            newSub.setId(id); // 복합키 설정
            newSub.setSubscribeStatus("Y"); // 구독 상태 Y로 등록
            newSub.setRegDate(LocalDateTime.now()); // 현재 시간으로 날짜 등록
            // 저장
            repository.save(newSub);
            // Y 상태이므로 true로 리턴
            return true;
        }
    }

    // ✅ 2. 현재 구독 상태 확인 (하트 색깔 표시용)
    public boolean isSubscribed(Long userId, Long menuId) {
        MenuSubscribeId id = new MenuSubscribeId(); // 복합키 생성
        id.setUserId(userId);
        id.setMenuId(menuId);

        // 해당 구독 정보 조회
        Optional<MenuSubscribe> optional = repository.findById(id);
        // 구독 정보가 존재하고 상태가 Y면 true (하트 빨간색)
        return optional.isPresent() && "Y".equals(optional.get().getSubscribeStatus());
    }


    public List<SubscribedMenuDTO> getSubscribedMenus(Long userId) {
        return repository.findSubscribedMenusByUserId(userId);
    }

}
