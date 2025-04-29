package com.iot7.controller;

import com.iot7.dto.SubscribeDTO;
import com.iot7.service.SubscribeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/subscribe")
public class SubscribeController {

    @Autowired
    private SubscribeService subscribeService;

    @PostMapping
    public void subscribe(@RequestBody SubscribeDTO subscribeDTO) {
        subscribeService.subscribe(subscribeDTO);
    }
}