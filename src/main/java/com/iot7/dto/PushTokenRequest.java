package com.iot7.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PushTokenRequest {
    private String userId;
    private String pushToken;
}
