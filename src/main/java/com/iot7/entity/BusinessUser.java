package com.iot7.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "BUSINESS_USER")
@Getter
@Setter
public class BusinessUser {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long businessId; //사업자 등록번호
        private String businessType; //본점인지 가맹점인지
        private String businessName; //프렌차이즈 이름
        private String sponsorshipYn; // 스폰서
    }
