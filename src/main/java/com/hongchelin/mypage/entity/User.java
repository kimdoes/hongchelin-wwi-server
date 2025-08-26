package com.hongchelin.mypage.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity @Table(name="users")
@Getter @NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable=false, length=30) private String nickname;
    private String profileImagePath;
    private Long activeBadgeId;
    private LocalDateTime createdAt; private LocalDateTime updatedAt;

    public static User createWithId(Long id, String nickname){
        User u = new User(); u.id = id; u.nickname = nickname; return u;
    }
    public void changeNickname(String nickname){ this.nickname = nickname; touch(); }
    public void changeProfileImage(String url){ this.profileImagePath = url; touch(); }
    public void setActiveBadge(Long badgeId){ this.activeBadgeId = badgeId; touch(); }

    @PrePersist void onCreate(){ createdAt = updatedAt = LocalDateTime.now(); }
    @PreUpdate  void onUpdate(){ updatedAt = LocalDateTime.now(); }
    private void touch(){ updatedAt = LocalDateTime.now(); }
}

