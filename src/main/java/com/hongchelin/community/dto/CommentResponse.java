package com.hongchelin.community.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentResponse {
    private Long id;
    private String content;
    private String writer;
    private Long postId;
}
