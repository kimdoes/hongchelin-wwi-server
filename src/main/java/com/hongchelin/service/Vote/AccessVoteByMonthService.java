package com.hongchelin.service.Vote;

import com.hongchelin.Domain.PastStoreForVote;
import com.hongchelin.Repository.PastStoreRepositoryInterface;
import com.hongchelin.dto.Response.PastStoreForVoteResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccessVoteByMonthService {
    private PastStoreRepositoryInterface pastStoreRepository;
    public AccessVoteByMonthService(PastStoreRepositoryInterface pastStoreRepository) {
        this.pastStoreRepository = pastStoreRepository;
    }

    public ResponseEntity<PastStoreForVoteResponseDTO> AccessVoteByMonth(String month) {
        List<PastStoreForVote> pastStoreForVoteList = pastStoreRepository.getPastStoreForVoteByWhenForVote(month);

        return ResponseEntity.ok(PastStoreForVoteResponseDTO.builder()
                .status(200)
                .message("성공")
                .whenForVote(month)
                .storeForPastVote(pastStoreForVoteList)
                .build());
    }
}
