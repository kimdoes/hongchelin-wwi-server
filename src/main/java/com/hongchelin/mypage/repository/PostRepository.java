// repository/PostRepository.java
package com.hongchelin.mypage.repository;
<<<<<<< HEAD
import com.hongchelin.mypage.entity.*;
import org.springframework.data.domain.*;
=======

import com.hongchelin.mypage.entity.Post;
import com.hongchelin.mypage.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
>>>>>>> feature/community
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    // 정렬 보장 페이징 + 최신 2개 조회용 메서드 추가
<<<<<<< HEAD
    Page<Post> findByAuthor(User author, org.springframework.data.domain.Pageable pageable);
=======
    Page<Post> findByAuthor(User author, Pageable pageable);
>>>>>>> feature/community
    List<Post> findTop2ByAuthorOrderByCreatedAtDesc(User author);

}

