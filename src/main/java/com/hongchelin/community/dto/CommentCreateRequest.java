package com.hongchelin.community.dto;

import lombok.Getter;

@Getter
public class CommentCreateRequest {
    private String author;
    private String content;
}
