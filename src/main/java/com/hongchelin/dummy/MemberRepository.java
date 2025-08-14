/*
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
        return jdbcTemplate.query("select id, nickname, role, refresh_token from Member",
                this::mapRowToMember);
    }

    public boolean checkingName(String name) {
        String sql = """
        SELECT COUNT(*) FROM MEMBER WHERE nickName = ?
        """;

        Integer count = jdbcTemplate.queryForObject
                (sql,
                        Integer.class,
                        name
                );

        return count != null && count == 0;
    }

    public boolean checkingId(String userId) {
        String sql = """
        SELECT COUNT(*) FROM MEMBER WHERE user_Id = ?
        """;

        Integer count = jdbcTemplate.queryForObject
                (sql,
                        Integer.class,
                        userId
                );

        return count != null && count == 0;
    }

    public boolean checkingEmail(Member member) {
        String sql = """
        SELECT COUNT(*) FROM MEMBER WHERE email = ?
        """;

        Integer count = jdbcTemplate.queryForObject
                (sql,
                        Integer.class,
                        member.getEmail()
                );

        return count != null && count == 0;
    }

    @Override
    public Member findById(Long id) {
        String sql = "select id, user_Id, nickname, password, identifier from Member where id=?";
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
*/
/*
    @Override
    public Member save(Member member) {
        long memberId = saveMemberInfo(member);
        member.setId(memberId);
        saveMemberToDB(member, memberId);

        return member;
    }

    private long saveMemberInfo(Member member) {
        PreparedStatementCreator psc =
                new PreparedStatementCreatorFactory(
                        "insert into MEMBER (email, nickname, password, user_Id) values (?, ?, ?, ?)",
                        Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR
                ).newPreparedStatementCreator(
                        Arrays.asList(
                                member.getEmail(),
                                member.getNickname(),
                                member.getPassword(),
                                member.getUserId()
                        ));

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(psc, keyHolder);

        return keyHolder.getKey().longValue();
    }

    private void saveMemberToDB(Member member, long memberId) {
        jdbcTemplate.update(
                "insert into MEMBER (id, email, nickname, password, user_Id) values (?, ?, ?, ?, ?)",
                memberId, member.getEmail(), member.getNickname(), member.getPassword(), member.getUserId()
        );
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
*/