package com.hongchelin.Repository;

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
        return jdbcTemplate.query("select id, store_Name, store_Location_In_MapX, store_Location_In_MapY, sotre_Img, store_Location from STORE",
                this::mapRowToStore);
        }

    public Store findById(Long id) {
        return jdbcTemplate.queryForObject(
                "select id, store_Name, store_Location_In_MapX, store_Location_In_MapY, sotre_Img, store_Location from STORE where id=?",
                this::mapRowToStore, id);
    }

    public boolean checker(Store store) {
        String sql = """
        SELECT COUNT(*) FROM STORE WHERE store_Name = ?
        AND ABS(store_Location_In_MapX - ?) < 0.00001
        AND ABS(store_Location_In_MapY - ?) < 0.00001
        """;

        Integer count = jdbcTemplate.queryForObject
                (sql,
                 Integer.class,
                 store.getStoreName(),
                 store.getStoreLocationInMapX(),
                 store.getStoreLocationInMapY()
                );

        return count != null && count == 0;
    }

    public Store save(Store store) {
        jdbcTemplate.update(
                "insert into Store (store_Name, store_Location_In_MapX, store_Location_In_MapY, store_Img, store_Location) values (?, ?, ?, ?, ?)",
                store.getStoreName(),
                store.getStoreLocationInMapX(),
                store.getStoreLocationInMapY(),
                store.getStoreImg(),
                store.getStoreLocation()
       );

        return store;
    }

    public Store mapRowToStore(ResultSet rs, int rowNum) throws SQLException {
        return Store.builder()
                .id(rs.getLong("id"))
                .storeName(rs.getString("name"))
                .storeLocationInMapX(rs.getString("locationInMap"))
                .storeLocationInMapY(rs.getString("locationInMapY"))
                .storeImg(rs.getString("storeImg"))
                .storeLocation(rs.getString("storeLocation"))
                .build();
    }
}

