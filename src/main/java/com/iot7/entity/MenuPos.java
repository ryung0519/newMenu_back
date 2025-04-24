package com.iot7.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "menu_pos")
@Getter
@Setter
@IdClass(MenuPosId.class)
@NoArgsConstructor
public class MenuPos {

    @Id
    @Column(name = "MENU_ID")
    private Long menuId;

    @Id
    @Column(name = "POS_ID")
    private Long posId;

    @Column(name = "SALE_STATUS")
    private Integer saleStatus;

    //JPA가 두 번 insert하거나 값 충돌을 일으키는 걸 방지하기 위해 필요
    @ManyToOne
    @JoinColumn(name = "MENU_ID", insertable = false, updatable = false)
    private Menu menu;

    @ManyToOne
    @JoinColumn(name = "POS_ID", insertable = false, updatable = false)
    private Pos pos;
}
