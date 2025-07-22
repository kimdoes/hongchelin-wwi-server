package com.hongchelin.Service.signup;

import com.hongchelin.dto.user.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ValidateIdService {
    public ResponseEntity<ResponseDTO> ValidateIdService(String id) {
        boolean isInDatabase = true;
        //DB확인하기


        if (isInDatabase) { //중복이 아닌 경우
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .status(200)
                    .message("사용가능한 이메일입니다.")
                    .validity(true)
                    .build();

            return ResponseEntity.ok(responseDTO);
        } else { //중복인경우
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .status(400)
                    .message("사용 중인 이메일입니다.")
                    .validity(false)
                    .build();

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(responseDTO);
        }
    }
}
