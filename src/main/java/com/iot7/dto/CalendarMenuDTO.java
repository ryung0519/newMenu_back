package com.iot7.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

//Getter메서드(외부 클래스에서 해당값을 가져올 수 있도록 함)
@Getter
@Data
@AllArgsConstructor
public class CalendarMenuDTO {
    //멤버 변수 정의
    private final String menuName;
    private final String category;
    private final LocalDateTime regDate;
    private final String brand;
    private String description;
    private int price;
    private String image;

    // menu.getBusinessUser().getBusinessName() (brand 가져올 방법)

}

