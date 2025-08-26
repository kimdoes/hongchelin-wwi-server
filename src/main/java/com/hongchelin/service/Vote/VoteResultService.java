package com.hongchelin.service.Vote;

import com.hongchelin.Domain.StoreForVote;
import com.hongchelin.Repository.StoreForVoteRepositoryInterface;
import com.hongchelin.dto.Response.StoreForVoteResponseDTO;
import com.hongchelin.dto.Response.StoreResponseEntityDTO;
import com.hongchelin.service.StoreConverterService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoteResultService {
    private final StoreForVoteRepositoryInterface storeForVoteRepository;
    private StoreConverterService storeConverterService;
    public VoteResultService(StoreForVoteRepositoryInterface storeForVoteRepository,
                             StoreConverterService storeConverterService) {
        this.storeForVoteRepository = storeForVoteRepository;
        this.storeConverterService = storeConverterService;
    }

    public ResponseEntity<StoreResponseEntityDTO> voteResult () {
        List<StoreForVote> voteList = getStoresForVote();

        return storeConverterService.convert(voteList);
    }

    public List<StoreForVote> getStoresForVote() {
        return storeForVoteRepository.findAll();
    }
}
