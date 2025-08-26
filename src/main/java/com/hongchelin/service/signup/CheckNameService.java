package com.hongchelin.service.signup;

import com.hongchelin.Repository.MemberRepositoryInterface;
import com.hongchelin.dto.user.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CheckNameService {
    private final MemberRepositoryInterface memberRepository;
    public CheckNameService(MemberRepositoryInterface memberRepository) {
        this.memberRepository = memberRepository;
    }

    public ResponseEntity<ResponseDTO> CheckName(String name) {
        boolean checkName = memberRepository.existsByNickname(name);

        if (!checkName) { //DB에 없는 경우 -> 중복안됨
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .status(200)
                    .message("중복되지 않습니다.")
                    .validity(true)
                    .build();

            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } else {
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .status(401)
                    .message("동일한 닉네임이 이미 있습니다.")
                    .validity(false)
                    .build();

            return new ResponseEntity<>(responseDTO, HttpStatus.UNAUTHORIZED);
        }
    }
}
