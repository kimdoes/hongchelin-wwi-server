//package com.hongchelin.community.controller;
//
//import com.hongchelin.community.dto.PostDtos;
//import com.hongchelin.community.service.PostService;
//import jakarta.validation.Valid;
//import org.springframework.data.domain.Page;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//// 기존: @RequestMapping("/api/community/posts")
//@RequestMapping({"/api/community/posts", "/community/posts"})
////@RequestMapping("/api/community/posts")
//public class PostController {
//
//    private final PostService posts;
//
//    public PostController(PostService posts) { this.posts = posts; }
//
//    // 목록 + 검색 (검색어 없으면 최신순)
//    @GetMapping
//    public PostDtos.PageResp<PostDtos.ListItem> list(
//            @RequestParam(required = false) String query,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size) {
//        Page<PostDtos.ListItem> pg = posts.search(query, page, size);
//        return PostDtos.PageResp.of(pg);
//    }
//
//    // 상세
//    @GetMapping("/{id}")
//    public PostDtos.Detail detail(@PathVariable Long id) {
//        return PostDtos.Detail.of(posts.get(id));
//    }
//
//    // 생성
//    @PostMapping
//    public java.util.Map<String, Object> create(@RequestHeader("X-USER-ID") Long userId,
//                                                @RequestBody @Valid PostDtos.CreateReq req) {
//        var saved = posts.create(userId, req);
//        return java.util.Map.of("id", saved.getId());
//    }
//
//    // 수정(작성자 본인만)
//    @PutMapping("/{id}")
//    public PostDtos.Detail edit(@RequestHeader("X-USER-ID") Long userId,
//                                @PathVariable Long id,
//                                @RequestBody @Valid PostDtos.UpdateReq req) {
//        return PostDtos.Detail.of(posts.edit(id, userId, req));
//    }
//
//    // 삭제(작성자 본인만)
//    @DeleteMapping("/{id}")
//    public void delete(@RequestHeader("X-USER-ID") Long userId,
//                       @PathVariable Long id) {
//        posts.delete(id, userId);
//    }
//}
//

// src/main/java/com/hongchelin/community/controller/PostController.java
package com.hongchelin.community.controller;

import com.hongchelin.community.dto.PostDtos;
import com.hongchelin.community.entity.Post;
import com.hongchelin.community.service.PostService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/community/posts")
public class PostController {

    private final PostService posts;

    public PostController(PostService posts) {
        this.posts = posts;
    }

    // 목록 (검색어 없으면 최신순)
    @GetMapping
    public PostDtos.PageResp<PostDtos.ListItem> list(
            @RequestParam(required = false) String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return PostDtos.PageResp.of(posts.search(query, page, size));
    }

    // 상세
    @GetMapping("/{postId}")
    public PostDtos.Detail detail(@PathVariable Long postId) {
        Post p = posts.get(postId);
        return PostDtos.Detail.of(p);
    }

    // 생성 (JSON만)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public PostDtos.Detail createJson(
            @RequestHeader(value="X-USER-ID", required=false) Long userId,
            @Valid @RequestBody PostDtos.CreateReq req
    ) {
        Long uid = (userId == null ? 1L : userId); // 기본값
        Post saved = posts.create(uid, req);
        return PostDtos.Detail.of(saved);
    }

    // 수정
    @PutMapping(value="/{postId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public PostDtos.Detail editJson(
            @RequestHeader(value="X-USER-ID", required=false) Long userId,
            @PathVariable Long postId,
            @Valid @RequestBody PostDtos.UpdateReq req
    ) {
        Long uid = (userId == null ? 1L : userId);
        Post edited = posts.edit(postId, uid, req);
        return PostDtos.Detail.of(edited);
    }

    // 삭제
    @DeleteMapping("/{postId}")
    public void delete(
            @RequestHeader(value="X-USER-ID", required=false) Long userId,
            @PathVariable Long postId
    ) {
        Long uid = (userId == null ? 1L : userId);
        posts.delete(postId, uid);
    }
}

