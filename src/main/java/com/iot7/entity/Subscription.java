package com.iot7.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "SUBSCRIBE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subscribe_seq_gen")
    @SequenceGenerator(name = "subscribe_seq_gen", sequenceName = "SUBSCRIBE_SEQ", allocationSize = 1)
    private Long id; // ✅ DB에서 만든 자동 증가 ID
    private Long userId;  // 유저 ID
    private Long businessId;  // 브랜드 고유의 ID
}
