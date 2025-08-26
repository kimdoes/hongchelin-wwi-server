package com.hongchelin.Repository;

import com.hongchelin.Domain.VoteRecord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteRecordRepository extends CrudRepository<VoteRecord, Long> {
    List<VoteRecord> findByUserId(String userId);
}
