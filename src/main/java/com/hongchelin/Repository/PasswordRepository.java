package com.hongchelin.Repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PasswordRepository {
    private JdbcTemplate jdbcTemplate;
    public PasswordRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String save (String pwd) {
        jdbcTemplate.update(
                "insert into PWD (number) values (?)",
                pwd
        );

        return pwd;
    }

    public String delete (String pwd) {
        jdbcTemplate.update(
                "DELETE FROM PWD WHERE (number) = (?)",
                pwd
        );

        return pwd;
    }

    public boolean checkingNumber(String pwd) {
        String sql = """
        SELECT COUNT(*) FROM PWD WHERE number = ?
        """;

        Integer count = jdbcTemplate.queryForObject
                (sql,
                        Integer.class,
                        pwd
                );

        return count != null && count == 1;
    }
}
