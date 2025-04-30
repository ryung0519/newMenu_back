package com.iot7.entity;

import java.io.Serializable;
import java.util.Objects;

//복합키 클래스
public class MenuPosId implements Serializable {
    private Long menuId;
    private Long posId;

    // 기본 생성자
    public MenuPosId() {}

    public MenuPosId(Long menuId, Long posId) {
        this.menuId = menuId;
        this.posId = posId;
    }

    // equals, hashCode 필수 구현
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MenuPosId)) return false;
        MenuPosId that = (MenuPosId) o;
        return Objects.equals(menuId, that.menuId) && Objects.equals(posId, that.posId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(menuId, posId);
    }
}