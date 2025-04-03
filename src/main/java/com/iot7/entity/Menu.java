package com.iot7.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "menu")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuId;
    private String menuName;
    private String category;
    private int price;
    @Transient
    private String description;
    @Transient
    private String image;
    @Transient
    private String brand;

    @Column(name = "reg_date")
    private LocalDateTime regDate;
}
