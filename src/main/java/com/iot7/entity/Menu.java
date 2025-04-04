package com.iot7.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "menu")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MENU_ID")
    private Long menuId;

    @Column(name = "MENU_NAME")
    private String menuName;

    @Column(name = "INGREDIENTS")
    private String ingredients;

    @Column(name = "PRICE")
    private int price;

    @Column(name = "CLICK_COUNT")
    private Long clickCount;

    @Column(name = "CALORIE")
    private Float calorie;

    @Column(name = "REG_DATE")
    private String regDate;

    @Column(name = "CATEGORY")
    private String category;

    @ManyToOne
    @JoinColumn(name = "BUSINESS_ID") //FK 컬럼명
    private BusinessUser businessUser;

}


