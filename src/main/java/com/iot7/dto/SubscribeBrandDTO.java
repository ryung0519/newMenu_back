package com.iot7.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SubscribeBrandDTO {
    private Long businessId;
    private String brandName;
}
