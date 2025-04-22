package com.iot7.dto;

import lombok.Getter;
import lombok.Setter;

/*지도 좌표 받아오기 위해 만든 파일 */


@Getter
@Setter
public class PosDTO {
    private String location; // 매장 이름
    private Double latitude; // 위도
    private Double longitude; // 경도
    private String businessName; // 브랜드명


}

