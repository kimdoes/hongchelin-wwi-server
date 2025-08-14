package com.hongchelin.community.controller;

import com.hongchelin.community.dto.CommentCreateRequest;
import com.hongchelin.community.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts/{postId}/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public Long addComment(@PathVariable Long postId,
                           @RequestBody CommentCreateRequest request) {
        return commentService.addComment(postId, request);
    }

    /** ✅ 댓글 삭제 API 추가 */
    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable Long postId,
                              @PathVariable Long commentId) {
        // postId는 경로 구조를 유지하기 위해 받지만 실제 삭제에는 commentId만 사용
        commentService.deleteComment(commentId);
    }
}
