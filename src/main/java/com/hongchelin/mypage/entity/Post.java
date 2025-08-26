package com.hongchelin.mypage.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter @NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class Post {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(optional=false) private User author;
    @Column(nullable=false, length=120) private String title;
    private String imagePath;
    private LocalDateTime createdAt;

    public static Post create(User author, String title, String imagePath){
        Post p = new Post(); p.author=author; p.title=title; p.imagePath=imagePath; return p;
    }
    @PrePersist void onCreate(){ createdAt = LocalDateTime.now(); }
}


