package com.hongchelin.Repository;

import com.hongchelin.Domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class MemberRepository implements MemberRepositoryInterface {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public MemberRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Iterable<Member> findAll() {
        return jdbcTemplate.query("select id, name, role, refresh_token from Member",
                this::mapRowToMember);
    }

    @Override
    public Member findById(Long id) {
        return jdbcTemplate.queryForObject(
                "select id, user_Id, nickname, password, identifier from Member where id=?",
                this::mapRowToMember, id);
    }

    @Override
    public Member save(Member member) {
        jdbcTemplate.update(
                "insert into Member (user_Id, nickname, password, email) values (?, ?, ?, ?)",
                member.getUserId(),
                member.getNickname(),
                member.getPassword(),
                member.getEmail()
        );
        return member;
    }

    private Member mapRowToMember(ResultSet rs, int rowNum) throws SQLException {
        return Member.builder()
                .id(rs.getLong("id"))
                .nickname(rs.getString("name"))
                .password(rs.getString("password"))
                .userId(rs.getString("user_Id"))
                .email(rs.getString("email"))
                .build();
    }
}
