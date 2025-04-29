package com.iot7.controller;

import com.iot7.dto.SubscribeDTO;
import com.iot7.service.SubscribeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subscribe")
public class SubscribeController {

    @Autowired
    private SubscribeService subscribeService;

    //✅ 버튼 한번 누르면 구독되거나 or 취소되는 컨트롤러)
    @PostMapping
    public boolean subscribeOrToggle(@RequestBody SubscribeDTO subscribeDTO) {
        return subscribeService.subscribeOrToggle(subscribeDTO);
        // 👉 등록되면 true, 취소되면 false 리턴
    }

    // ✅ 구독 여부 확인 (프론트에서 브랜드 진입 시 하트 상태용)
    @GetMapping("/check")
    public boolean isSubscribed(@RequestParam Long userId, @RequestParam Long businessId) {
        return subscribeService.isSubscribed(userId, businessId);
    }
}
