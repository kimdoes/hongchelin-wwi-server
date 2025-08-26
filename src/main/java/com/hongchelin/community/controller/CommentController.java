package com.hongchelin.community.controller;

import com.hongchelin.community.dto.CommentDtos;
import com.hongchelin.community.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/community")
public class CommentController {

    private final CommentService comments;

    public CommentController(CommentService comments) {
        this.comments = comments;
    }

    // 댓글 목록
    @GetMapping("/posts/{postId}/comments")
    public CommentDtos.ListResp list(@PathVariable Long postId) {
        return CommentDtos.ListResp.of(comments.list(postId));
    }

    // 댓글 작성
    @PostMapping("/posts/{postId}/comments")
    public java.util.Map<String, Object> add(@RequestHeader("X-USER-ID") Long userId,
                                             @PathVariable Long postId,
                                             @RequestBody @jakarta.validation.Valid CommentDtos.CreateReq req) {
        var saved = comments.add(postId, userId, req);
        return java.util.Map.of("id", saved.getId());
    }

    // 댓글 삭제
    @DeleteMapping("/comments/{commentId}")
    public void delete(@RequestHeader("X-USER-ID") Long userId,
                       @PathVariable Long commentId) {
        comments.delete(commentId, userId);
    }
}


