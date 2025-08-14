package com.hongchelin.community.service;

import com.hongchelin.community.dto.CommentCreateRequest;
import com.hongchelin.community.entity.Comment;
import com.hongchelin.community.entity.Post;
import com.hongchelin.community.repository.CommentRepository;
import com.hongchelin.community.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostService postService;

    public Long addComment(Long postId, CommentCreateRequest request) {
        Post post = postService.getPost(postId);

        Comment comment = Comment.builder()
                .author(request.getAuthor())
                .content(request.getContent())
                .build();

        post.addComment(comment);
        return commentRepository.save(comment).getId();
    }

    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));
        commentRepository.delete(comment);
    }
}



