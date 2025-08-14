package com.hongchelin.service.signup;

import com.hongchelin.Repository.PasswordRepository;
import com.hongchelin.dto.user.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailCheckerService {
    private PasswordRepository passwordRepository;
    @Autowired
    public EmailCheckerService(PasswordRepository passwordRepository) {
        this.passwordRepository = passwordRepository;
    }

    @Async
    public ResponseEntity<ResponseDTO> EmailCheckerService(Integer userPwd) {
        boolean pwd = passwordRepository.checkingNumber(userPwd);

        if (pwd) {
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .status(200)
                    .message("성공")
                    .validity(true)
                    .build();

            passwordRepository.delete(userPwd);

            return ResponseEntity.ok(responseDTO);
        } else {
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .status(401)
                    .message("인증번호가 일치하지 않습니다.")
                    .validity(false)
                    .build();

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(responseDTO);
        }
    }
}
