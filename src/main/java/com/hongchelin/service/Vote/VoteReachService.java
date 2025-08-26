package com.hongchelin.service.Vote;

import com.hongchelin.Domain.Member;
import com.hongchelin.Domain.StoreForVote;
import com.hongchelin.Repository.MemberRepositoryInterface;
import com.hongchelin.Repository.StoreForVoteRepositoryInterface;
import com.hongchelin.exceptions.UnauthorizedException;
import com.hongchelin.service.JWT.JWTFilter;
import com.hongchelin.dto.Response.StoreForVoteResponseDTO;
import com.hongchelin.dto.user.MemberDTO;
import com.hongchelin.service.StoreConverterService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoteReachService {
    private final JWTFilter jwtFilter;
    private final MemberRepositoryInterface memberRepository;
    private final StoreForVoteRepositoryInterface storeForVoteRepository;
    private final StoreConverterService storeConverterService;

    public VoteReachService(JWTFilter jwtFilter,
                            MemberRepositoryInterface memberRepository,
                            StoreForVoteRepositoryInterface storeForVoteRepository,
                            StoreConverterService storeConverterService) {
        this.jwtFilter = jwtFilter;
        this.memberRepository = memberRepository;
        this.storeForVoteRepository = storeForVoteRepository;
        this.storeConverterService = storeConverterService;
    }

    public ResponseEntity<StoreForVoteResponseDTO> VoteService(String secret, HttpServletRequest request) throws UnauthorizedException {
        boolean validity = jwtFilter.getTokenFromHeader(secret, request).getValidity();

        if (!validity) {
            throw new UnauthorizedException();
        }

        MemberDTO memberDTO = jwtFilter.getTokenFromHeader(secret, request).getMemberInfo();

        if (memberDTO != null) { //회원
            String userId = memberDTO.getIdentifier();

            if (memberRepository.findByUserId(userId).size() == 0 ) {
                StoreForVoteResponseDTO storeForVoteResponseDTO = StoreForVoteResponseDTO.builder()
                        .status(400)
                        .message("사용자정보가 없습니다.")
                        .voteAvailable(false)
                        .build();

                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(storeForVoteResponseDTO);
            }

            Member member = memberRepository.findByUserId(userId).get(0);
            System.out.println(member.isVoteAvailable());

            if (member != null) {
                if (!member.isVoteAvailable()) { //이미 투표함. 투표 불가능
                    List<StoreForVote> voteList = getStoresForVote();

                    StoreForVoteResponseDTO storeForVoteResponseDTO = StoreForVoteResponseDTO.builder()
                            .status(200)
                            .message("성공(이미 투표한 사용자)")
                            .storeForVote(storeConverterService.convert(voteList).getBody().getStores())
                            .voteAvailable(false)
                            .build();

                    return ResponseEntity.ok(storeForVoteResponseDTO);
                } else {
                    List<StoreForVote> voteList = getStoresForVote();

                    StoreForVoteResponseDTO storeForVoteResponseDTO = StoreForVoteResponseDTO.builder()
                            .status(200)
                            .message("성공(투표가능한 사용자")
                            .storeForVote(storeConverterService.convert(voteList).getBody().getStores())
                            .voteAvailable(true)
                            .build();

                    return ResponseEntity.ok((storeForVoteResponseDTO));
                }
            } else { //유저정보가 없음
                StoreForVoteResponseDTO storeForVoteResponseDTO = StoreForVoteResponseDTO.builder()
                        .status(400)
                        .message("유저의 정보가 없습니다.")
                        .voteAvailable(false)
                        .build();

                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(storeForVoteResponseDTO);
            }
        } else { //회원아님
            StoreForVoteResponseDTO storeForVoteResponseDTO = StoreForVoteResponseDTO.builder()
                    .status(400)
                    .message("회원이 아닙니다.")
                    .build();

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(storeForVoteResponseDTO);
        }
    }

    public List<StoreForVote> getStoresForVote() {
        return storeForVoteRepository.findAll();
    }
}
