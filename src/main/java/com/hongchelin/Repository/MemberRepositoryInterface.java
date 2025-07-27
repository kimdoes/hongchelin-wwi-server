package com.hongchelin.Repository;

import com.hongchelin.Domain.Member;

public interface MemberRepositoryInterface {
    Iterable<Member> findAll();
    Member findById(Long id);
    Member save(Member member);
}
