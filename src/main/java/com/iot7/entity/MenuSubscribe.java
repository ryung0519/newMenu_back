package com.iot7.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "MENU_SUBSCRIBE")
@Getter
@Setter
@NoArgsConstructor
public class MenuSubscribe {

    @EmbeddedId // ✅ 복합키를 사용하겠다는 뜻
    private MenuSubscribeId id;

    @Column(name = "REG_DATE") // ✅ 구독 등록 일자
    private LocalDateTime regDate;

    @Column(name = "SUBSCRIBE_STATUS") // ✅ 구독 상태 ('Y' 또는 'N')
    private String subscribeStatus;
}
