package com.hongchelin.community.service;

import com.hongchelin.community.dto.CommentDtos;
import com.hongchelin.community.entity.Comment;
import com.hongchelin.community.repository.CommentRepository;
import com.hongchelin.community.repository.PostRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    private final CommentRepository comments;
    private final PostRepository posts;
    private final AuthorSnapshotService author;

    public CommentService(CommentRepository comments, PostRepository posts, AuthorSnapshotService author) {
        this.comments = comments; this.posts = posts; this.author = author;
    }

    public Comment add(Long postId, Long userId, CommentDtos.CreateReq req) {
        var post = posts.findById(postId).orElseThrow(() -> new IllegalArgumentException("게시글 없음"));
        var a = author.get(userId);
        var c = Comment.create(post, a.id(), a.nickname(), a.profileImageUrl(), req.content());
        return comments.save(c);
    }

    public void delete(Long commentId, Long userId) {
        var c = comments.findById(commentId).orElseThrow(() -> new IllegalArgumentException("댓글 없음"));
        if (!c.ownedBy(userId)) throw new SecurityException("삭제 권한이 없습니다.");
        comments.delete(c);
    }

    public java.util.List<Comment> list(Long postId) {
        var p = posts.findById(postId).orElseThrow(() -> new IllegalArgumentException("게시글 없음"));
        return comments.findByPostOrderByCreatedAtAsc(p);
    }
}



