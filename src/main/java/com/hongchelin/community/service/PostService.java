package com.hongchelin.community.service;

import com.hongchelin.community.dto.PostCreateRequest;
import com.hongchelin.community.dto.PostResponse;
import com.hongchelin.community.dto.PostUpdateRequest;
import com.hongchelin.community.entity.Post;
import com.hongchelin.community.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import jakarta.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Long createPost(PostCreateRequest request) {
        Post post = Post.builder()
                .title(request.getTitle())
                .createdDate(request.getDate())
                .location(request.getLocation())
                .recommendedMenu(request.getRecommendedMenu())
                .content(request.getContent())
                .imagePath(request.getImagePath())
                .rating(request.getRating())
                .build();

        return postRepository.save(post).getId();
    }

    public List<PostResponse> getAllPosts() {
        return postRepository.findAll().stream()
                .map(PostResponse::new)
                .toList();
    }

    public Post getPost(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
    }

    @Transactional
    public Long updatePost(Long id, PostUpdateRequest request) {
        Post post = getPost(id); // 존재하는지 확인
        post.update(request);
        return post.getId();
    }

    public void deletePost(Long id) {
        Post post = getPost(id);
        postRepository.delete(post);
    }
}
