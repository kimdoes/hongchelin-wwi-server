// repository/PostRepository.java
package com.hongchelin.mypage.repository;
import com.hongchelin.mypage.entity.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByAuthor(User author, Pageable pageable);
}

