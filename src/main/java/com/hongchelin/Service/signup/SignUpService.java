package com.hongchelin.Service.signup;

import com.hongchelin.Domain.Member;
import com.hongchelin.Repository.MemberRepository;
import com.hongchelin.dto.Request.MemberRequestDTO;
import com.hongchelin.dto.user.ResponseDTO;
import jakarta.validation.Valid;
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
        String nickname = memberRequestDTO.getNickname();
        String userId = memberRequestDTO.getUserId();
        String password = memberRequestDTO.getPassword();
        String email = memberRequestDTO.getEmail();

        if (nickname == null || email == null) {
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .status(400)
                    .message("닉네임 또는 이메일이 공백입니다.")
                    .build();

            return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
        } else {
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
        }
    }
}
