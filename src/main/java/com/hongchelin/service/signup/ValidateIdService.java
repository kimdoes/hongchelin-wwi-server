package com.hongchelin.service.signup;

import com.hongchelin.Repository.MemberRepositoryInterface;
import com.hongchelin.dto.user.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ValidateIdService {
    private final MemberRepositoryInterface memberRepository;
    public ValidateIdService(MemberRepositoryInterface memberRepository) {
        this.memberRepository = memberRepository;
    }

    public ResponseEntity<ResponseDTO> ValidateIdService(String id) {
        if (id == null || id.isEmpty()) {
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .status(400)
                    .message("아이디를 정확하게 입력해주세요.")
                    .validity(false)
                    .build();

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(responseDTO);
        }

       boolean isInDatabase = memberRepository.existsByUserId(id);

        if (!isInDatabase) { //중복이 아닌 경우 (DB에 없는 경우)
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .status(200)
                    .message("사용가능한 아이디입니다.")
                    .validity(true)
                    .build();

            return ResponseEntity.ok(responseDTO);
        } else { //중복인경우
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .status(400)
                    .message("사용 중인 아이디입니다.")
                    .validity(false)
                    .build();

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(responseDTO);
        }
    }
}
