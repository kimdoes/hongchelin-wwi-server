package com.hongchelin.community.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "community_comment")
public class Comment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Post_c post;

    // 작성자 스냅샷(댓글)
    private Long authorId;
    private String authorNickname;
    private String authorProfileImageUrl;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createdAt;

    public static Comment create(Post_c post, Long authorId, String nickname,
                                 String profileImageUrl, String content) {
        Comment c = new Comment();
        c.post = post;
        c.authorId = authorId;
        c.authorNickname = nickname;
        c.authorProfileImageUrl = profileImageUrl;
        c.content = content;
        c.createdAt = LocalDateTime.now();
        return c;
    }

    public boolean ownedBy(Long userId) {
        return this.authorId != null && this.authorId.equals(userId);
    }
}
