package com.hongchelin.Repository;

import com.hongchelin.Domain.PastStoreForVote;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PastStoreRepositoryInterface extends CrudRepository<PastStoreForVote, Long> {
    List<PastStoreForVote> getPastStoreForVoteByWhenForVote(String whenForVote);

    PastStoreForVote findByIdAndWhenForVote(Long id, String whenForVote);
}
