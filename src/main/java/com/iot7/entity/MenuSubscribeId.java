package com.iot7.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable // 복합키 클래스임을 명시
@Getter
@Setter
@NoArgsConstructor
public class MenuSubscribeId implements Serializable { // 복합키는 반드시 Serializable에 구현해야함

    private Long userId;
    private Long menuId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MenuSubscribeId)) return false;
        MenuSubscribeId that = (MenuSubscribeId) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(menuId, that.menuId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, menuId);
    }
}
