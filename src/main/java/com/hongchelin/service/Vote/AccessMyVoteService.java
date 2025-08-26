package com.hongchelin.service.Vote;

import com.hongchelin.Domain.PastStoreForVote;
import com.hongchelin.Domain.VoteRecord;
import com.hongchelin.Repository.PastStoreRepositoryInterface;
import com.hongchelin.Repository.VoteRecordRepository;
import com.hongchelin.dto.Response.PastStoreForVoteResponseDTO;
import com.hongchelin.exceptions.UnauthorizedException;
import com.hongchelin.service.JWT.JWTFilter;
import com.hongchelin.service.StoreConverterService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccessMyVoteService {
    private JWTFilter jwtFilter;
    private VoteRecordRepository voteRecordRepository;
    private PastStoreRepositoryInterface pastStoreRepository;
    private final StoreConverterService storeConverterService;

    public AccessMyVoteService(JWTFilter jwtFilter,
                               VoteRecordRepository voteRecordRepository,
                               PastStoreRepositoryInterface pastStoreRepository,
                               StoreConverterService storeConverterService) {
        this.jwtFilter = jwtFilter;
        this.voteRecordRepository = voteRecordRepository;
        this.pastStoreRepository = pastStoreRepository;
        this.storeConverterService = storeConverterService;
    }

    public ResponseEntity<PastStoreForVoteResponseDTO> accessMyVoteService(HttpServletRequest request, String secret) throws UnauthorizedException {
        boolean validity = jwtFilter.getTokenFromHeader(secret, request).getValidity();

        if (!validity) {
            throw new UnauthorizedException();
        }

        String userId = jwtFilter.getTokenFromHeader(secret, request).getMemberInfo().getIdentifier();

        List<VoteRecord> recordList = voteRecordRepository.findByUserId(userId);
        System.out.println(recordList);
        List<PastStoreForVote> pastStoreForVoteList = new ArrayList<>();

        for (VoteRecord record : recordList) {
            System.out.println(record);
            System.out.println(record.getVotedId());

            long votedId = record.getVotedId();
            String whenForVote = record.getWhenForVote();

            PastStoreForVote pastStoreForVote = pastStoreRepository.findByIdAndWhenForVote(votedId, whenForVote);
            pastStoreForVoteList.add(pastStoreForVote);
        }

        PastStoreForVoteResponseDTO pastStoreForVoteResponseDTO = PastStoreForVoteResponseDTO.builder()
                .status(200)
                .message("성공")
                .storeForPastVote(storeConverterService.convertPast(pastStoreForVoteList).getBody().getStores())
                .build();

        return ResponseEntity.ok(pastStoreForVoteResponseDTO);
    }
}
