package com.iot7.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class MenuCombinationId implements Serializable {

    @NotNull
    @Column(name = "MENU_ID")
    private Long menuId;

    @NotNull
    @Column(name = "USER_ID", length = 50)
    private String userId;

    @NotNull
    @Column(name = "PAIRED_MENU_ID")
    private Long pairedMenuId;

    public MenuCombinationId() {}

    public MenuCombinationId(Long menuId, String userId, Long pairedMenuId) {
        this.menuId = menuId;
        this.userId = userId;
        this.pairedMenuId = pairedMenuId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MenuCombinationId that)) return false;
        return Objects.equals(menuId, that.menuId) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(pairedMenuId, that.pairedMenuId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(menuId, userId, pairedMenuId);
    }
}
