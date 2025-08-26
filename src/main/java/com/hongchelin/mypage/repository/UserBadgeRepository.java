// repository/UserBadgeRepository.java
package com.hongchelin.mypage.repository;

import com.hongchelin.mypage.entity.User;
import com.hongchelin.mypage.entity.UserBadge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface UserBadgeRepository extends JpaRepository<UserBadge, Long> {
    List<UserBadge> findByUser(User user);
    boolean existsByUserIdAndBadgeId(Long userId, Long badgeId);
}
