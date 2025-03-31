package com.iot7.dto;

import lombok.Getter;

import java.time.LocalDateTime;

//Getter메서드(외부 클래스에서 해당값을 가져올 수 있도록 함)
@Getter
public class CalendarMenuDTO {
    //멤버 변수 정의
    private final String menuName;
    private final String category;
    private final LocalDateTime regDate;

    //생성자( CalendarMenuDTO 객체를 생성할 때, menuName, category, regDate 값을 받아와서 멤버 변수에 저장)
    public CalendarMenuDTO(String menuName, String category, LocalDateTime regDate) {
        this.menuName = menuName;
        this.category = category;
        this.regDate = regDate;
    }
}
