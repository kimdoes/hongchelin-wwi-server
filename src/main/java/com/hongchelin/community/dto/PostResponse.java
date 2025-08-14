package com.hongchelin.community.dto;

import com.hongchelin.community.entity.Post;
import lombok.Getter;

@Getter
public class PostResponse {
    private Long id;
    private String title;
    private String location;
    private int rating;

    public PostResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.location = post.getLocation();
        this.rating = post.getRating();
    }
}