// src/main/java/com/hongchelin/community/service/PostService.java
package com.hongchelin.community.service;

import com.hongchelin.community.dto.PostDtos_c;
import com.hongchelin.community.entity.Post_c;
import com.hongchelin.community.repository.PostRepository_c;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class PostService_c {
    private final PostRepository_c posts;
    private final AuthorSnapshotService author;

    public PostService_c(PostRepository_c posts, AuthorSnapshotService author) {
        this.posts = posts; this.author = author;
    }

    private String norm(String s) {
        return StringUtils.hasText(s) ? s.trim() : null;
    }

    public Post_c create(Long userId, PostDtos_c.CreateReq req) {
        var a = author.get(userId);
        var p = Post_c.create(
                a.id(), a.nickname(), a.profileImageUrl(), a.badgeIconUrl(),
                req.title(), req.restaurantName(), norm(req.location()),   // location null 허용
                req.recommendedMenu(), req.content(), req.rating(), req.imageUrl()
        );
        return posts.save(p);
    }

    public Post_c edit(Long id, Long userId, PostDtos_c.UpdateReq req) {
        var p = posts.findById(id).orElseThrow(() -> new IllegalArgumentException("게시글 없음"));
        if (!p.ownedBy(userId)) throw new SecurityException("수정 권한이 없습니다.");
        p.edit(req.title(), req.restaurantName(), norm(req.location()),
                req.recommendedMenu(), req.content(), req.rating(), req.imageUrl());
        return posts.save(p);
    }

    public void delete(Long id, Long userId) {
        var p = posts.findById(id).orElseThrow(() -> new IllegalArgumentException("게시글 없음"));
        if (!p.ownedBy(userId)) throw new SecurityException("삭제 권한이 없습니다.");
        posts.delete(p);
    }

    public Post_c get(Long id) {
        return posts.findById(id).orElseThrow(() -> new IllegalArgumentException("게시글 없음"));
    }

    public Page<PostDtos_c.ListItem> search(String q, int page, int size) {
        var pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate")); //
        var pageObj = (q == null || q.isBlank())
                ? posts.findAllByOrderByCreatedDateDesc(pageable) //
                : posts.findByTitleContainingIgnoreCaseOrRestaurantNameContainingIgnoreCaseOrContentContainingIgnoreCase(
                q, q, q, pageable);
        return pageObj.map(PostDtos_c.ListItem::of);
    }
}


