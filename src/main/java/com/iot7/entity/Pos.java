package com.iot7.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/*지도 좌표 받아오기 위해 만든 파일 */


@Entity
@Table(name = "POS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POS_ID")
    private Long posId;

    @ManyToOne
    @JoinColumn(name = "BUSINESS_ID", nullable = false) //연결할 외래키를 명시해주는것 = Join
    private BusinessUser businessUser; // 브랜드명 접근을 위해 추가

    @Column(name = "LOCATION") // 지역
    private String location;

    @Column(name = "LATITUDE")
    private Double latitude;

    @Column(name = "LONGITUDE")
    private Double longitude;

    @Column(name = "POS_LOGIN_ID", nullable = false, unique = true)
    private String posLoginId;

    @Column(name = "POS_PASSWORD", nullable = false)
    private String posPassword;

    //localMenu를 가져오기 위한 POS 연결
    @OneToMany(mappedBy = "pos", cascade = CascadeType.ALL)
    private List<MenuPos> menuPosList = new ArrayList<>();

}

