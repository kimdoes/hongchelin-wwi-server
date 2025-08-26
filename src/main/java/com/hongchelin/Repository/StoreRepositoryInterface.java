package com.hongchelin.Repository;

import com.hongchelin.Domain.Store;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface StoreRepositoryInterface extends CrudRepository<Store, String> {
    Store findById(Long id);
    List<Store> findByStoreName(String storeName);
    Integer countByStoreLocation(String storeLocation);
    Integer countByStoreNameAndStoreLocation(String storeName, String storeLocation);
    Store findByStoreNameAndStoreLocation(String storeName, String storeLocation);

    Store findByStoreLocation(String storeLocation);

    boolean existsByStoreNameAndStoreLocation(String storeName, String storeLocation);
}
