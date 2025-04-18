package com.iot7.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Review {
    @EmbeddedId
    private ReviewId id;

    @MapsId("menuId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "MENU_ID", nullable = false)
    private Menu menu;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @Size(max = 1000)
    @Column(name = "REVIEW_CONTENT", length = 1000)
    private String reviewContent;

    @Column(name = "REVIEW_RATING")
    private Short reviewRating;

}