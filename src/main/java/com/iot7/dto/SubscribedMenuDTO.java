package com.iot7.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SubscribedMenuDTO {
    private Long menuId;
    private String menuName;
    private String imageUrl;
    private String description;
}
