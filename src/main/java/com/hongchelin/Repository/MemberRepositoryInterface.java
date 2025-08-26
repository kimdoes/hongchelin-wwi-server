package com.hongchelin.Repository;

import com.hongchelin.Domain.Member;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepositoryInterface extends CrudRepository<Member, String> {
    List<Member> findByUserId(String userId);
    boolean existsByUserId(String userId);
    boolean existsByEmail(String email);
    boolean existsByNickname(String name);

    boolean existsByUserIdOrEmailOrNickname(String userId, String email, String nickname);

    boolean existsBySocialEmail(String socialEmail);

    Optional<Member> findBySocialEmail(String socialEmail);

    Integer countByUserId(String userId);

    Integer countByUserIdAndPassword(String userId, String password);
}
