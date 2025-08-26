// repository/BadgeRepository.java
package com.hongchelin.mypage.repository;
<<<<<<< HEAD
import com.hongchelin.mypage.entity.Badge;
import org.springframework.data.jpa.repository.JpaRepository;
=======

import com.hongchelin.mypage.entity.Badge;
import org.springframework.data.jpa.repository.JpaRepository;

>>>>>>> feature/community
import java.util.Optional;
public interface BadgeRepository extends JpaRepository<Badge, Long> {
    Optional<Badge> findByCode(String code);
}
