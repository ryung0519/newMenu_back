package com.iot7.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "MENU_CLICK_LOG")
public class MenuClickLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 시퀀스 + 트리거 자동 주입을 위한 설정
    private Long id;

    @Column(name = "MENU_ID", nullable = false)
    private Long menuId;

    @Column(name = "CLICK_TIME")
    private LocalDateTime clickTime;
}