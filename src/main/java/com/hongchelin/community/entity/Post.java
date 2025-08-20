package com.hongchelin.community.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//@Getter
//@NoArgsConstructor
//@Entity
//@Table(name = "community_post")
//public class Post {
//
//    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    // 작성자 스냅샷
//    private Long authorId;
//    private String authorNickname;
//    private String authorProfileImageUrl;   // null 가능
//    private String authorBadgeIconUrl;      // null 가능
//
//    // 본문
//    @Column(nullable = false, length = 120)
//    private String title;
//    @Column(nullable = false, length = 80)
//    private String restaurantName;          // 식당 이름
//    @Column(nullable = false, length = 80)
//    private String location;
//    @Column(nullable = false, length = 80)
//    private String recommendedMenu;
//    @Column(nullable = false, columnDefinition = "TEXT")
//    private String content;
//    @Column(nullable = false)
//    private Integer rating;                 // 1~5
//    private String imageUrl;                // 썸네일/대표이미지 (선택)
//
//    private LocalDateTime createdAt;
//    private LocalDateTime updatedAt;
//
//    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, orphanRemoval = true)
//    private List<Comment> comments = new ArrayList<>();
//
//    public static Post create(Long authorId, String authorNickname, String authorProfileImageUrl,
//                              String authorBadgeIconUrl,
//                              String title, String restaurantName, String location,
//                              String recommendedMenu, String content, Integer rating, String imageUrl) {
//
//        Post p = new Post();
//        p.authorId = authorId;
//        p.authorNickname = authorNickname;
//        p.authorProfileImageUrl = authorProfileImageUrl;
//        p.authorBadgeIconUrl = authorBadgeIconUrl;
//        p.title = title;
//        p.restaurantName = restaurantName;
//        p.location = location;
//        p.recommendedMenu = recommendedMenu;
//        p.content = content;
//        p.rating = rating;
//        p.imageUrl = imageUrl;
//        p.createdAt = LocalDateTime.now();
//        p.updatedAt = p.createdAt;
//        return p;
//    }
//
//    public void edit(String title, String restaurantName, String location,
//                     String recommendedMenu, String content, Integer rating, String imageUrl) {
//        this.title = title;
//        this.restaurantName = restaurantName;
//        this.location = location;
//        this.recommendedMenu = recommendedMenu;
//        this.content = content;
//        this.rating = rating;
//        this.imageUrl = imageUrl;
//        this.updatedAt = LocalDateTime.now();
//    }
//
//    public boolean ownedBy(Long userId) {
//        return this.authorId != null && this.authorId.equals(userId);
//    }
//}

// import jakarta.persistence.*; import lombok.*; import java.time.*;

// src/main/java/com/hongchelin/community/entity/Post.java

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "community_post")
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 작성자 스냅샷
    private Long authorId;
    private String authorNickname;
    private String authorProfileImageUrl;   // null 가능
    private String authorBadgeIconUrl;      // null 가능

    // 본문
    @Column(nullable = false, length = 120)
    private String title;

    @Column(nullable = false, length = 80)
    private String restaurantName;          // 식당 이름

    // location 을 선택값으로 변경 (null 허용)
    @Column(name = "location", length = 80)   // ← nullable=false 제거
    private String location;

    @Column(nullable = false, length = 80)
    private String recommendedMenu;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private Integer rating;                 // 1~5

    private String imageUrl;                // 썸네일/대표이미지 (선택)

    // 필드명은 createdDate/updatedDate 로, 기존 컬럼(created_at/updated_at)과 매핑
    @Column(name = "created_at")
    private LocalDateTime createdDate;

    @Column(name = "updated_at")
    private LocalDateTime updatedDate;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDateTime.now();
        this.updatedDate = this.createdDate;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedDate = LocalDateTime.now();
    }

    public static Post create(Long authorId, String authorNickname, String authorProfileImageUrl,
                              String authorBadgeIconUrl,
                              String title, String restaurantName, String location,
                              String recommendedMenu, String content, Integer rating, String imageUrl) {
        Post p = new Post();
        p.authorId = authorId;
        p.authorNickname = authorNickname;
        p.authorProfileImageUrl = authorProfileImageUrl;
        p.authorBadgeIconUrl = authorBadgeIconUrl;
        p.title = title;
        p.restaurantName = restaurantName;
        p.location = location;              // null 가능
        p.recommendedMenu = recommendedMenu;
        p.content = content;
        p.rating = rating;
        p.imageUrl = imageUrl;
        return p; // @PrePersist 에서 날짜 세팅
    }

    public void edit(String title, String restaurantName, String location,
                     String recommendedMenu, String content, Integer rating, String imageUrl) {
        this.title = title;
        this.restaurantName = restaurantName;
        this.location = location;
        this.recommendedMenu = recommendedMenu;
        this.content = content;
        this.rating = rating;
        this.imageUrl = imageUrl;
        // @PreUpdate 가 updatedDate 갱신
    }

    public boolean ownedBy(Long userId) {
        return this.authorId != null && this.authorId.equals(userId);
    }
}
