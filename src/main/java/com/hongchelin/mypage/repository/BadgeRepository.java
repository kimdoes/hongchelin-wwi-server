// repository/BadgeRepository.java
package com.hongchelin.mypage.repository;
import com.hongchelin.mypage.entity.Badge;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface BadgeRepository extends JpaRepository<Badge, Long> {
    Optional<Badge> findByCode(String code);
}
