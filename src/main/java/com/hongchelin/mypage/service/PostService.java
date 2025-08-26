// service/PostService.java
package com.hongchelin.mypage.service;

import com.hongchelin.mypage.entity.Post;
import com.hongchelin.mypage.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    private final PostRepository posts; private final UserService users;
    public PostService(PostRepository posts, UserService users){ this.posts = posts; this.users = users; }
    public Page<Post> myPosts(Long userId, int page, int size) {
        // 최신순으로 정렬 보장
        var sort = Sort.by(Sort.Direction.DESC, "createdAt");
        return posts.findByAuthor(users.getOrCreate(userId), PageRequest.of(page, size, sort));
    }

    public List<Post> latestTwo(Long userId) {
        return posts.findTop2ByAuthorOrderByCreatedAtDesc(users.getOrCreate(userId));
    }
}

