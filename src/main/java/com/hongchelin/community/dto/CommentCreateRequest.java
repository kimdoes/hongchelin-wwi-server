package com.hongchelin.community.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CommentCreateRequest {
    private String content;
    private String writer;
}
