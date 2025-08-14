package com.hongchelin.community.controller;

import com.hongchelin.community.dto.PostCreateRequest;
import com.hongchelin.community.dto.PostResponse;
import com.hongchelin.community.dto.PostUpdateRequest;
import com.hongchelin.community.entity.Post;
import com.hongchelin.community.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public Long createPost(@RequestBody PostCreateRequest request) {
        return postService.createPost(request);
    }

    @GetMapping
    public List<PostResponse> getPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/posts/{postId}")
    public Post getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    @PutMapping("/posts/{postId}")
    public Long updatePost(@PathVariable Long id,
                           @RequestBody PostUpdateRequest request) {
        return postService.updatePost(id, request);
    }

    @DeleteMapping("/posts/{postId}")
    public void deletePost(@PathVariable Long id) {
        postService.deletePost(id);
    }
}



