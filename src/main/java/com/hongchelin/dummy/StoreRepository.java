/*
package com.hongchelin.dummy;

import com.hongchelin.Domain.Store;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class StoreRepository {
    private JdbcTemplate jdbcTemplate;

    public StoreRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Iterable<Store> findAll() {
        return jdbcTemplate.query("select id, store_Name, store_Img, store_Location, store_Info_Oneline from STORE",
                this::mapRowToStore);
        }

    public Store findById(Long id) {
        return jdbcTemplate.queryForObject(
                "select id, store_Name, store_Img, store_Location, store_Info_Oneline  from STORE where id=?",
                this::mapRowToStore, id);
    }

    public boolean checker(Store store) {
        String sql = """
        SELECT COUNT(*) FROM STORE WHERE (STORE_LOCATION) = ?
        """;

        Integer count = jdbcTemplate.queryForObject
                (sql,
                 Integer.class,
                 //store.getStoreName(),
                 store.getStoreLocation()
                );

        return count != null && count == 0;
    }

    public Store save(Store store) {
        jdbcTemplate.update(
                "insert into Store (store_Name, store_Img, store_Location, store_Info_Oneline) values (?, ?, ?, ?)",
                store.getStoreName(),
                store.getStoreImg(),
                store.getStoreLocation(),
                store.getStoreInfoOneline()
       );

        return store;
    }

    public Store findByStoreName(String storeName) {
        return jdbcTemplate.queryForObject(
                "select id, store_Name, store_Img, store_Location, store_Info_Oneline  from STORE where STORE_NAME=?",
                this::mapRowToStore, storeName);
    }

    public Store mapRowToStore(ResultSet rs, int rowNum) throws SQLException {
        return Store.builder()
                .id(rs.getLong("id"))
                .storeName(rs.getString("name"))
                .storeInfoOneline(rs.getString("store_info_oneline"))
                .storeImg(rs.getString("storeImg"))
                .storeLocation(rs.getString("storeLocation"))
                .build();
    }
}

 */

