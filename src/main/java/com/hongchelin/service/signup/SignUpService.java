package com.hongchelin.service.signup;

import com.hongchelin.Domain.Member;
import com.hongchelin.Repository.MemberRepositoryInterface;
import com.hongchelin.dto.Request.MemberRequestDTO;
import com.hongchelin.dto.user.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SignUpService {
    private final MemberRepositoryInterface memberRepository;
    public SignUpService(MemberRepositoryInterface memberRepository) {
        this.memberRepository = memberRepository;
    }

    public ResponseEntity<ResponseDTO> signUp(MemberRequestDTO memberRequestDTO) throws Exception {
        String nickname = memberRequestDTO.getNickname();
        String userId = memberRequestDTO.getUserId();
        String password = memberRequestDTO.getPassword();
        String email = memberRequestDTO.getEmail();

        if (nickname == null || nickname.isEmpty()) { //userId 와 password 는 validity 검사 대상임
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .status(400)
                    .message("닉네임을 입력해주세요")
                    .build();

            return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
        } else if (email == null || email.isEmpty()) {
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .status(400)
                    .message("이메일 주소를 입력해주세요")
                    .build();

            return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
        }
        else {
            boolean isAlreadyExist = memberRepository.existsByUserIdOrEmailOrNickname(
                    userId, email, nickname);

            if (isAlreadyExist) {
                ResponseDTO responseDTO = ResponseDTO.builder()
                        .status(400)
                        .message("이미 가입한 사용자입니다.")
                        .build();

                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(responseDTO);
            }

            Member member = Member.builder()
                    .nickname(nickname)
                    .userId(userId)
                    .password(password)
                    .email(email)
                    .voteAvailable(true)
                    .build();

            System.out.println("회원정보가 입력됨" + member);
            memberRepository.save(member);
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .status(200)
                    .message("성공")
                    .build();

            return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
        }
    }
}
