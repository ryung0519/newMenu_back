package com.iot7.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "USER_ACCOUNT")
public class User {

    @Id
    @Column(name = "USER_ID", nullable = false, unique = true)
    private String userId;

    @Column(name = "USER_NAME", nullable = false, length = 100)
    private String userName;


    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "PREFERRED_FOOD")
    private String preferredFood;

    @Column(name = "ALLERGIC_FOOD")
    private String allergicFood;

    @Column(name = "REG_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date regDate;
}
