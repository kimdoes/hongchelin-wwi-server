package com.hongchelin.community.repository;

import com.hongchelin.community.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByWriter(String writer);

    List<Post> findByTitleContaining(String keyword);

    List<Post> findAllByOrderByIdDesc();

    @Query("SELECT p FROM Post p LEFT JOIN p.comments c GROUP BY p.id ORDER BY COUNT(c) DESC")
    List<Post> findPostsOrderByCommentCount();
}
