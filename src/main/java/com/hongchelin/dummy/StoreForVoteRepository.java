/*
package com.hongchelin.dummy;

import com.hongchelin.Domain.Store;
import com.hongchelin.Domain.StoreForVote;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class StoreForVoteRepository {
    private JdbcTemplate jdbcTemplate;

    public StoreForVoteRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Iterable<StoreForVote> findAll() {
        return jdbcTemplate.query("select id, store_Name, store_Img, store_Location, store_Info_Oneline from STOREFORVOTE",
                this::mapRowToVoteStore);
    }

    public StoreForVote findById(Long id) {
        return jdbcTemplate.queryForObject(
                "select id, store_Name, store_Img, store_Location, store_Info_Oneline  from STOREFORVOTE where id=?",
                this::mapRowToVoteStore, id);
    }

    public boolean checker(String location) {
        String sql = """
        SELECT COUNT(*) FROM STORE WHERE (STORE_LOCATION) = ?
        """;

        try {
            Integer count = jdbcTemplate.queryForObject
                    (sql,
                            Integer.class,
                            //store.getStoreName(),
                            location
                    );

            return count != null && count == 1;
        } catch(Exception e) {
            return false;
        }
    }

    public Store save(Store store) {
        jdbcTemplate.update(
                "insert into STOREFORVOTE (store_Name, store_Img, store_Location, store_Info_Oneline) values (?, ?, ?, ?)",
                store.getStoreName(),
                store.getStoreImg(),
                store.getStoreLocation(),
                store.getStoreInfoOneline()
        );

        return store;
    }

    public StoreForVote mapRowToVoteStore(ResultSet rs, int rowNum) throws SQLException {
        return StoreForVote.builder()
                .id(rs.getLong("id"))
                .storeName(rs.getString("name"))
                .storeInfoOneline(rs.getString("store_info_oneline"))
                .storeImg(rs.getString("storeImg"))
                .storeLocation(rs.getString("storeLocation"))
                .build();
    }
}
*/