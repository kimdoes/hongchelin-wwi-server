// service/PostService.java
package com.hongchelin.mypage.service;

import com.hongchelin.mypage.entity.*;
import com.hongchelin.mypage.repository.PostRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    private final PostRepository posts; private final UserService users;
    public PostService(PostRepository posts, UserService users){ this.posts = posts; this.users = users; }
    public Page<Post> myPosts(Long userId, int page, int size){
        return posts.findByAuthor(users.getOrCreate(userId), PageRequest.of(page, size));
    }
}
