package com.hongchelin.community.dto;

import lombok.Getter;

@Getter
public class PostUpdateRequest {
    private String title;
    private String location;
    private String recommendedMenu;
    private String content;
    private String imagePath;
    private int rating;
}