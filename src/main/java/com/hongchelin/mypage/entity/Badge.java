package com.hongchelin.mypage.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter @NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class Badge {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(unique=true, nullable=false, length=50) private String code; // 예: WELCOME
    @Column(nullable=false, length=50) private String name; // 예: 환영 배지
    private String description;
    private String iconPath; // /files/badges/...

    public static Badge create(String code, String name, String desc, String icon){
        Badge b = new Badge(); b.code=code; b.name=name; b.description=desc; b.iconPath=icon; return b;
    }
}
