package com.hongchelin.service.Vote;

import com.hongchelin.Domain.*;
import com.hongchelin.Repository.*;
import com.hongchelin.exceptions.UnauthorizedException;
import com.hongchelin.service.JWT.JWTFilter;
import com.hongchelin.dto.Request.voteRequstDTO;
import com.hongchelin.dto.Response.StoreForVoteResponseDTO;
import com.hongchelin.dto.user.ResponseDTO;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class VoteService {
    private final JWTFilter jwtFilter;
    private final MemberRepositoryInterface memberRepository;
    private final StoreForVoteRepositoryInterface storeForVoteRepository;
    private final StoreRepositoryInterface storeRepositoryInterface;
    private final VoteRecordRepository voteRecordRepository;
    private final PastStoreRepositoryInterface pastStoreRepositoryInterface;

    public VoteService(JWTFilter jwtFilter,
                       MemberRepositoryInterface memberRepository,
                       StoreForVoteRepositoryInterface storeForVoteRepository,
                       StoreRepositoryInterface storeRepositoryInterface,
                       VoteRecordRepository voteRecordRepository, PastStoreRepositoryInterface pastStoreRepositoryInterface) {
        this.jwtFilter = jwtFilter;
        this.memberRepository = memberRepository;
        this.storeForVoteRepository = storeForVoteRepository;
        this.storeRepositoryInterface = storeRepositoryInterface;
        this.voteRecordRepository = voteRecordRepository;
        this.pastStoreRepositoryInterface = pastStoreRepositoryInterface;
    }

    public ResponseEntity<StoreForVoteResponseDTO> voteMainService(
            String secret,
            voteRequstDTO voteRequstDTO,
            HttpServletRequest request) throws UnauthorizedException {

        Date date = new Date();
        List<Long> votedIds = voteRequstDTO.getVotedIdList();
        List<StoreForVote> storeForVoteList = new ArrayList<>(); //저장용 리스트 / 저장할 때 사용함

        boolean validity = jwtFilter.getTokenFromHeader(secret, request).getValidity();

        if (!validity) {
            throw new UnauthorizedException();
        }
        ResponseDTO responseDTO = jwtFilter.getTokenFromHeader(secret, request);

        if (responseDTO == null) {                                   //토큰이 없을 경우
            StoreForVoteResponseDTO storeForVoteResponseDTO = StoreForVoteResponseDTO.builder()
                    .status(400)
                    .message("토큰이 전달되지 않았습니다.")
                    .build();

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(storeForVoteResponseDTO);
        }

        String userId = responseDTO.getMemberInfo().getIdentifier();
        boolean voteAvailable = memberRepository.findByUserId(userId).get(0).isVoteAvailable();   //사용자 투표여부 조사

        if (!voteAvailable) {         //이미투표한사용자
            StoreForVoteResponseDTO storeForVoteResponseDTO = StoreForVoteResponseDTO.builder()
                    .status(200)
                    .message("이미 투표한 사용자입니다.")
                    .build();

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(storeForVoteResponseDTO);
        }

        if (votedIds == null || votedIds.size() == 0) {     //사용자가 투표한 상점들의 id 리스트 - 메서드 인자로 제공됨
            StoreForVoteResponseDTO storeForVoteResponseDTO = StoreForVoteResponseDTO.builder()
                    .status(400)
                    .message("반드시 하나를 선택해주세요.")
                    .build();

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(storeForVoteResponseDTO);
        }

        if (votedIds.size() > 3) {     //3개이상 선택불가
            StoreForVoteResponseDTO storeForVoteResponseDTO = StoreForVoteResponseDTO.builder()
                    .status(400)
                    .message("3개 이상은 선택이 불가합니다.")
                    .build();

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(storeForVoteResponseDTO);
        }

        for (Long id : votedIds) {              //리스트를 순회하며 아이디를 따고 상점정보를 가져와 저장함
            Optional<StoreForVote> storeForVoteInOptional = storeForVoteRepository.findById(id);

            if (storeForVoteInOptional.isPresent()) {       //DB에 값이 있는 경우
                StoreForVote storeForVote = storeForVoteInOptional.get();
                storeForVoteList.add(storeForVote);

                Integer votedCount = storeForVote.getVotedCount() + 1;
                storeForVote.setVotedCount(votedCount);
                storeForVoteRepository.save(storeForVote);
            } else {                                        //DB에 값이 없는 경우 - 저장 취소 및 오류 반환
                StoreForVoteResponseDTO storeForVoteResponseDTO = StoreForVoteResponseDTO.builder()
                        .status(400)
                        .message("입력된 값이 존재하지 않습니다.")
                        .build();

                storeForVoteList.clear();

                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(storeForVoteResponseDTO);
            }
        }

        for (StoreForVote storeForVote : storeForVoteList) {    // 인자로 받은 값들이 모두 정상. DB에 저장하는 과정
            storeForVoteRepository.save(storeForVote);
        }

        StoreForVoteResponseDTO storeForVoteResponseDTO = StoreForVoteResponseDTO.builder()
                .status(200)
                .message("성공")
                .build();
        modifyUserVoteAvailable(userId);

        for (long ids : votedIds) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
            String formattedDate = formatter.format(date);

            VoteRecord voteRecord = VoteRecord.builder()
                    .userId(userId)
                    .votedId(ids)
                    .whenForVote(formattedDate)
                    .build();

            voteRecordRepository.save(voteRecord);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(storeForVoteResponseDTO);
    }

    public ResponseDTO modifyUserVoteAvailable(String userId) {
        Member member = memberRepository.findByUserId(userId).get(0);
        member.setVoteAvailable(false);

        memberRepository.save(member);

        return ResponseDTO.builder()
                .status(200)
                .build();
    }
}