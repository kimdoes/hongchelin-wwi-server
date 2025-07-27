/**
 * 홍익대 로그인 성공 시의 기능 (아직 미구현) / 토큰 발급
 *
 */

package com.hongchelin.Service.login.HongikUniv;

import com.hongchelin.Service.JWT.JWTFilter;
import com.hongchelin.dto.user.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class hongikUnivloginSuccessService {
    JWTFilter jwtFilter;
    public hongikUnivloginSuccessService(JWTFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    public ResponseEntity<ResponseDTO> checkpwd(Map<String, String> request, String secret) {
        String realpwd = "hii"; //Redis 도입 후 수정 예정
        String enteredpwd = request.get("pwd");

        if (realpwd.equals(enteredpwd)) {
            return hongikUnivlogin(secret);
        } else {
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .status(401)
                    .message("잘못된 인증번호입니다.")
                    .build();

            return ResponseEntity
                    .status(401)
                    .body(responseDTO);
        }
    }

    public ResponseEntity<ResponseDTO> hongikUnivlogin(String secret) {
        String identifier = "hi"; //이메일주소. 어딘가에 저장해두고 가져와야함

        String userToken = jwtFilter.createToken(secret, identifier);
        String refreshToken = jwtFilter.createToken(secret, identifier);

        ResponseDTO responseDTO = ResponseDTO.builder()
                .status(200)
                .redirectUrl("/")
                .accessToken(userToken)
                .refreshToken(refreshToken)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }
}
