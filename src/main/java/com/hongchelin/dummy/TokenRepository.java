/*
package com.hongchelin.Repository;

import com.hongchelin.Domain.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class TokenRepository {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public TokenRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public Iterable<Token> findAll() {
        return jdbcTemplate.query("select id, user_Id, refresh_token from Token",
                this::mapRowToToken);
    }

    public Token findById(Long id) {
        return jdbcTemplate.queryForObject(
                "select id, user_Id, refresh_token from Member where id=?",
                this::mapRowToToken, id);
    }

    public Token save(Token token) {
        jdbcTemplate.update(
                "insert into Token (user_Id, refresh_token) values (?, ?)",
                token.getUserId(),
                token.getRefreshToken()
        );
        return token;
    }

    public Token update(Token token) {
        jdbcTemplate.update(
                "update Token set refresh_token = ? where user_Id = ?",
                token.getRefreshToken(),
                token.getUserId()
        );

        return token;
    }

    private Token mapRowToToken(ResultSet rs, int rowNum) throws SQLException {
        return Token.builder()
                .id(rs.getLong("id"))
                .userId(rs.getString("user_Id"))
                .refreshToken(rs.getString("refresh_token"))
                .build();
    }
}
 */