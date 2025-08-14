package com.hongchelin.community.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Builder;
import com.hongchelin.community.dto.PostUpdateRequest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private LocalDate createdDate;

    private String location;

    private String recommendedMenu;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String imagePath;

    private int rating;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public Post(String title, LocalDate createdDate, String location, String recommendedMenu,
                String content, String imagePath, int rating) {
        this.title = title;
        this.createdDate = createdDate;
        this.location = location;
        this.recommendedMenu = recommendedMenu;
        this.content = content;
        this.imagePath = imagePath;
        this.rating = rating;
    }

    // 댓글 추가 메서드
    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setPost(this);
    }

    // 수정 메서드 (setter 대신 사용)
    public void update(PostUpdateRequest request) {
        this.title = request.getTitle();
        this.location = request.getLocation();
        this.recommendedMenu = request.getRecommendedMenu();
        this.content = request.getContent();
        this.imagePath = request.getImagePath();
        this.rating = request.getRating();
    }
}
