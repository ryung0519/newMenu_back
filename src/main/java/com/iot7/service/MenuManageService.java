package com.iot7.service;

import com.iot7.dto.MenuRegisterRequestDTO;
import com.iot7.entity.BusinessUser;
import com.iot7.entity.Menu;
import com.iot7.repository.BusinessUserRepository;
import com.iot7.repository.MenuRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class MenuManageService {

    private final BusinessUserRepository businessUserRepository;
    private final MenuRepository menuRepository;
    private final PushTokenService pushTokenService;

    public MenuManageService(BusinessUserRepository businessUserRepository,
                             MenuRepository menuRepository,
                             PushTokenService pushTokenService) {
        this.businessUserRepository = businessUserRepository;
        this.menuRepository = menuRepository;
        this.pushTokenService = pushTokenService;
    }

    public String registerMenu(MenuRegisterRequestDTO requestDTO, Long businessId) {
        //사업자 존재 여부 확인
        Optional<BusinessUser> optionalBusinessUser = businessUserRepository.findById(businessId);
        if (optionalBusinessUser.isEmpty()) {
            return "등록 실패: 사업자가 존재하지 않습니다.";
        }

        Menu menu = Menu.builder()
                .menuName(requestDTO.getMenuName())
                .category(requestDTO.getCategory())
                .price(requestDTO.getPrice())
                .calorie(requestDTO.getCalorie())
                .ingredients(requestDTO.getIngredients())
                .dietYn(requestDTO.isDietYn() ? "Y" : "N")
                .businessUser(optionalBusinessUser.get())
                .regDate(LocalDateTime.now())
                .image(requestDTO.getImageUrl())
                .description(requestDTO.getDescription())
                .build();

        // 메뉴 저장
        menuRepository.save(menu);

        //알림 보내는 서비스를 호출하는 코드
        pushTokenService.saveMenuAndNotify(menu);

        return "메뉴 등록 성공!";
    }
}
