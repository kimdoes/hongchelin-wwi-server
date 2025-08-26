// repository/UserBadgeRepository.java
package com.hongchelin.mypage.repository;
<<<<<<< HEAD
import com.hongchelin.mypage.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
=======

import com.hongchelin.mypage.entity.User;
import com.hongchelin.mypage.entity.UserBadge;
import org.springframework.data.jpa.repository.JpaRepository;

>>>>>>> feature/community
import java.util.List;
public interface UserBadgeRepository extends JpaRepository<UserBadge, Long> {
    List<UserBadge> findByUser(User user);
    boolean existsByUserIdAndBadgeId(Long userId, Long badgeId);
}
