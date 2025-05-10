package com.iot7.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter //
@NoArgsConstructor // 기본 생성자 자동 생성
public class MenuSubscribeDTO {

    private Long userId;
    private Long menuId;
}
