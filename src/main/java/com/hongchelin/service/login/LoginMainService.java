package com.hongchelin.service.login;

import com.hongchelin.Domain.Token;
import com.hongchelin.Repository.MemberRepositoryInterface;
import com.hongchelin.Repository.TokenRepositoryInterface;
import com.hongchelin.service.JWT.JWTFilter;
import com.hongchelin.dto.Request.MemberRequestDTO;
import com.hongchelin.dto.user.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class LoginMainService {
    private final MemberRepositoryInterface memberRepository;
    private final TokenRepositoryInterface tokenRepository;
    private final JWTFilter jwtFilter;

    public LoginMainService(MemberRepositoryInterface memberRepository,
                            JWTFilter jwtFilter,
                            TokenRepositoryInterface tokenRepository) {
        this.memberRepository = memberRepository;
        this.jwtFilter = jwtFilter;
        this.tokenRepository = tokenRepository;
    }

    public ResponseEntity<ResponseDTO> login(String secret, MemberRequestDTO memberRequestDTO) {
        System.out.println(memberRequestDTO);
        System.out.println("요청확인");

        String userId = memberRequestDTO.getUserId();
        String password = memberRequestDTO.getPassword();

        Integer count = memberRepository.countByUserIdAndPassword(userId, password);

        if (count == 1) { //정보 있음. 로그인
            String refreshToken = jwtFilter.createRefreshToken(secret, userId);

            ResponseDTO responseDTO = ResponseDTO.builder()
                    .status(200)
                    .message("성공")
                    .accessToken(jwtFilter.createToken(secret, userId))
                    .refreshToken(refreshToken)
                    .build();

            Token token = Token.builder()
                    .userId(userId)
                    .refreshToken(refreshToken)
                    .build();

            tokenRepository.save(token);
            System.out.println(responseDTO);

            return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
        } else { //로그인 정보 없음
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .status(400)
                    .message("아이디 또는 비밀번호가 올바르지 않습니다.")
                    .build();

            System.out.println(responseDTO);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
        }
    }
}
