package com.hongchelin.mypage.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter @NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class Badge {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(unique=true, nullable=false, length=50) private String code;
    @Column(nullable=false, length=50) private String name;
    private String description;
    private String iconPath;

    public static Badge create(String code, String name, String description, String icon){
        Badge b = new Badge(); b.code=code; b.name=name; b.description=description; b.iconPath=icon; return b;
    }
}
