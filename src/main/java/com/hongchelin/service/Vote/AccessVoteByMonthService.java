package com.hongchelin.service.Vote;

import com.hongchelin.Domain.PastStoreForVote;
import com.hongchelin.Repository.PastStoreRepositoryInterface;
import com.hongchelin.dto.Response.PastStoreForVoteResponseDTO;
import com.hongchelin.service.StoreConverterService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccessVoteByMonthService {
    private PastStoreRepositoryInterface pastStoreRepository;
    private final StoreConverterService storeConverterService;
    public AccessVoteByMonthService(PastStoreRepositoryInterface pastStoreRepository,
                                    StoreConverterService storeConverterService) {
        this.pastStoreRepository = pastStoreRepository;
        this.storeConverterService = storeConverterService;
    }

    public ResponseEntity<PastStoreForVoteResponseDTO> AccessVoteByMonth(String month) {
        List<PastStoreForVote> pastStoreForVoteList = pastStoreRepository.getPastStoreForVoteByWhenForVote(month);

        return ResponseEntity.ok(PastStoreForVoteResponseDTO.builder()
                .status(200)
                .message("성공")
                .whenForVote(month)
                .storeForPastVote(storeConverterService.convertPast(pastStoreForVoteList).getBody().getStores())
                .build());
    }
}
