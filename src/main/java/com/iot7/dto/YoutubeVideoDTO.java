package com.iot7.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class YoutubeVideoDTO {
    private String videoId; //
    private String title; // 제목
    private String thumbnailUrl; // 썸네일
}
