package com.hongchelin.Repository;

import com.hongchelin.Domain.StoreForVote;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface StoreForVoteRepositoryInterface extends CrudRepository<StoreForVote, Long> {
    Integer countByStoreId(long storeId);
    List<StoreForVote> findAll();

    Optional<StoreForVote> findById(Long id);
    List<StoreForVote> findTop3ByOrderByVotedCountDesc();
    void deleteByStoreId(long storeId);

    StoreForVote findByStoreId(long storeId);
}
