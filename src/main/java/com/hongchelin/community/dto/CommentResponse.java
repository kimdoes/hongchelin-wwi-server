package com.hongchelin.community.dto;

import com.hongchelin.community.entity.Comment;
import lombok.Getter;

@Getter
public class CommentResponse {
    private Long id;
    private String author;
    private String content;

    public CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.author = comment.getAuthor();
        this.content = comment.getContent();
    }
}
