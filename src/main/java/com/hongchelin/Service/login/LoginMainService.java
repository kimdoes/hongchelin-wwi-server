package com.hongchelin.Service.login;

import com.hongchelin.Domain.Member;
import com.hongchelin.Domain.Token;
import com.hongchelin.Repository.MemberRepository;
import com.hongchelin.Repository.TokenRepository;
import com.hongchelin.Service.JWT.JWTFilter;
import com.hongchelin.dto.Request.MemberRequestDTO;
import com.hongchelin.dto.user.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class LoginMainService {
    private final JdbcTemplate jdbcTemplate;
    private final MemberRepository memberRepository;
    private final TokenRepository tokenRepository;
    private final JWTFilter jwtFilter;

    public LoginMainService(JdbcTemplate jdbcTemplate, MemberRepository memberRepository, JWTFilter jwtFilter, TokenRepository tokenRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.memberRepository = memberRepository;
        this.jwtFilter = jwtFilter;
        this.tokenRepository = tokenRepository;
    }

    public ResponseEntity<ResponseDTO> login(String secret, MemberRequestDTO memberRequestDTO) {
        String userId = memberRequestDTO.getUserId();
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM member WHERE user_Id = ?", Integer.class, userId);

        if (count == 1) { //정보 있음. 로그인
            String ideintifier = userId;
            String passWord = jdbcTemplate.queryForObject("SELECT password FROM MEMBER WHERE user_Id = ?", String.class, userId);
            String enteredPassword = memberRequestDTO.getPassword();

            if (passWord.equals(enteredPassword)) {
                ResponseDTO responseDTO = ResponseDTO.builder()
                        .status(200)
                        .message("성공")
                        .accessToken(jwtFilter.createToken(secret, ideintifier))
                        .refreshToken(jwtFilter.createRefreshToken(secret, ideintifier))
                        .build();

                Integer countToken = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM TOKEN WHERE user_id = ?", Integer.class, userId);

                Token token = Token.builder()
                        .userId(userId)
                        .refreshToken(jwtFilter.createRefreshToken(secret, ideintifier))
                        .build();

                if (countToken == 1) {
                    tokenRepository.update(token);
                } else {
                    tokenRepository.save(token);
                }

                return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
            } else { //메시지 오류
                ResponseDTO responseDTO = ResponseDTO.builder()
                        .status(400)
                        .message("비밀번호가 일치하지 않습니다")
                        .build();

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseDTO);
            }
        } else { //로그인 정보 없음
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .status(400)
                    .message("없는 아이디입니다.")
                    .build();

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
        }
    }
}
