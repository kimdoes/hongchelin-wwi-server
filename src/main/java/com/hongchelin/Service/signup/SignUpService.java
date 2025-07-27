package com.hongchelin.Service.signup;

import com.hongchelin.Domain.Member;
import com.hongchelin.Repository.MemberRepository;
import com.hongchelin.dto.Request.MemberRequestDTO;
import com.hongchelin.dto.user.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SignUpService {
    private final MemberRepository memberRepository;
    public SignUpService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public ResponseEntity<ResponseDTO> signUp(MemberRequestDTO memberRequestDTO) throws Exception {
        try {
            String nickname = memberRequestDTO.getNickname();
            String userId = memberRequestDTO.getUserId();
            String password = memberRequestDTO.getPassword();
            String email = memberRequestDTO.getEmail();

            Member member = Member.builder()
                    .nickname(nickname)
                    .userId(userId)
                    .password(password)
                    .email(email)
                    .build();

            memberRepository.save(member);
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .status(200)
                    .message("성공")
                    .build();

            return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
        } catch(Exception e) {
            e.printStackTrace();
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .status(500)
                    .message("에러")
                    .build();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
        }
    }
}
