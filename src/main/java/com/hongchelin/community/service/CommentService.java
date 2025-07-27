package com.hongchelin.community.service;

import com.hongchelin.community.entity.Comment;
import com.hongchelin.community.entity.Post;
import com.hongchelin.community.repository.CommentRepository;
import com.hongchelin.community.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public Comment addComment(Long postId, Comment comment) {
        Post post = postRepository.findById(postId).orElseThrow();
        comment.setPost(post);
        return commentRepository.save(comment);
    }
}
