// src/main/java/com/hongchelin/community/repository/PostRepository.java
package com.hongchelin.community.repository;

import com.hongchelin.community.entity.Post_c;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository_c extends JpaRepository<Post_c, Long> {

    Page<Post_c> findAllByOrderByCreatedDateDesc(Pageable pageable); //

    Page<Post_c> findByTitleContainingIgnoreCaseOrRestaurantNameContainingIgnoreCaseOrContentContainingIgnoreCase(
            String title, String restaurantName, String content, Pageable pageable
    );
}

