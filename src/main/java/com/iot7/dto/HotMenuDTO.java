package com.iot7.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class HotMenuDTO {
    private Long menuId;
    private String menuName;
    private String imageUrl;
}
