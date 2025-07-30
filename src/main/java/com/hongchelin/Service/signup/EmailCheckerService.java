package com.hongchelin.Service.signup;

import com.hongchelin.dto.user.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class EmailCheckerService {

    public ResponseEntity<ResponseDTO> EmailCheckerService(String userPwd) {
        //Redis 에서 저장된 pwd 가져오기
        String pwd = "thehiveclusterisunderattack";

        if (userPwd.equals(pwd)) {
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .status(200)
                    .message("성공")
                    .build();

            return ResponseEntity.ok(responseDTO);
        } else {
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .status(401)
                    .message("인증번호가 일치하지 않습니다.")
                    .build();

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(responseDTO);
        }
    }
}
