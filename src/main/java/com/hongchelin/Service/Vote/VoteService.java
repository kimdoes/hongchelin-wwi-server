package com.hongchelin.Service.Vote;

import com.hongchelin.Service.JWT.JWTFilter;
import com.hongchelin.dto.Request.voteRequstDTO;
import com.hongchelin.dto.user.MemberDTO;
import com.hongchelin.dto.user.ResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class VoteService {
    private final JWTFilter jwtFilter;
    public VoteService(JWTFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    public ResponseEntity<ResponseDTO> VoteService(String secret, HttpServletRequest request, voteRequstDTO voteRequestDTO) {
        MemberDTO memberDTO = jwtFilter.getTokenFromHeader(secret, request).getMemberInfo();

        if (memberDTO != null) {
            boolean isInToken = jwtFilter.getTokenFromHeader(secret, request).getValidity();

            if (isInToken) {
                String identifier = memberDTO.getIdentifier();

                // DB의 Store에 선택한 유저의 정보를 저장해야함

                // 프론트에서 storeIdentifier 변수에 투표했다는 요청을 보냈고
                // DB 에서 이를 이용해서 +1을 해주어야함

                //String storeIdentifier = voteRequestDTO.getStoreIdentifier();
                String storeIdentifier = "328328328";

                if (storeIdentifier != null) {
                    //Integer votedAmount = voteRequestDTO.getSelectedAmount();
                    Integer votedAmount = 328;
                    votedAmount += 1;
                    //DB 에서 해당 storeIdentifier 에 +1을 해주어야함

                    ResponseDTO responseDTO = ResponseDTO.builder()
                            .status(200)
                            .message("성공! " + "투표가게 " + storeIdentifier + " 총 투표수 " + votedAmount)
                            .build();

                    return ResponseEntity.ok(responseDTO);
                } else {
                    ResponseDTO responseDTO = ResponseDTO.builder()
                            .status(400)
                            .message("잘못된 가게에 투표했습니다.")
                            .build();

                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
                }
            }
        } else {
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .status(400)
                    .message("사용자 정보가 제대로 전달되지 못했습니다.")
                    .build();

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
        }

        ResponseDTO responseDTO = ResponseDTO.builder()
                .message("권한이 없습니다.")
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseDTO);
    }
}
