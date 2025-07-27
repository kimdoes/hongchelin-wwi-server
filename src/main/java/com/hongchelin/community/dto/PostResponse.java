package com.hongchelin.community.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostResponse {
    private Long id;
    private String title;
    private String content;
    private String writer;
    private int commentCount;
}
