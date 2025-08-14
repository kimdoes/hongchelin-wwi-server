package com.hongchelin.service.Vote;

import com.hongchelin.Domain.StoreForVote;
import com.hongchelin.Repository.StoreForVoteRepositoryInterface;
import com.hongchelin.dto.Response.StoreForVoteResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class VoteResultService {
    private final StoreForVoteRepositoryInterface storeForVoteRepository;
    public VoteResultService(StoreForVoteRepositoryInterface storeForVoteRepository) {
        this.storeForVoteRepository = storeForVoteRepository;
    }

    public ResponseEntity<StoreForVoteResponseDTO> voteResult () {
        Iterable<StoreForVote> voteList = getStoresForVote();

        StoreForVoteResponseDTO storeForVoteResponseDTO = StoreForVoteResponseDTO.builder()
                .status(200)
                .message("성공")
                .storeForVote(voteList)
                .build();

        return ResponseEntity.ok(storeForVoteResponseDTO);
    }

    public Iterable<StoreForVote> getStoresForVote() {
        return storeForVoteRepository.findAll();
    }
}
