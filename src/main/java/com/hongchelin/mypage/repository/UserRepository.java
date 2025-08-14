// repository/UserRepository.java
package com.hongchelin.mypage.repository;
import com.hongchelin.mypage.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
public interface UserRepository extends JpaRepository<User, Long> {}
