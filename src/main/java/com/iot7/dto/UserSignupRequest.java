package com.iot7.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignupRequest {
    private String uid; // Firebase ID Token
    private String name;
    private String email;
    private String password;
    private String preferredFood;
    private String allergicFood;
}
