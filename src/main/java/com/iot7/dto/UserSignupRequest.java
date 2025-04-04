package com.iot7.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignupRequest {
    private String uid;
    private String name;
    private String email;
    private String password;
    private String preferredFood;
    private String allergicFood;
}
