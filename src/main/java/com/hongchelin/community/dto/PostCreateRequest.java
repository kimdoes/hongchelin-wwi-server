package com.hongchelin.community.dto;

import lombok.Getter;
import java.time.LocalDate;

@Getter
public class PostCreateRequest {
    private String title;
    private LocalDate date;
    private String location;
    private String recommendedMenu;
    private String content;
    private String imagePath;
    private int rating;
}
