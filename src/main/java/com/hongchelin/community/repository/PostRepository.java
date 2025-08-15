package com.hongchelin.community.repository;

import com.hongchelin.community.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findByTitleContainingIgnoreCaseOrRestaurantNameContainingIgnoreCaseOrContentContainingIgnoreCase(
            String title, String restaurantName, String content, Pageable pageable);

    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
