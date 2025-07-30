/**
 * JWT AccessToken이 만료될 경우 RefreshToken이 유효할 경우 새 AccessToken을 발급하고
 * RefreshToken이 유효하지 않을 경우 로그인창으로 이동시키는 코드입니다.
 *
 * @author dx2d2y
 * @version 1.0
 * @param request HttpServletRequest 객체로 프론트엔드에서 헤더에 RefreshToken을 담아보내기에 RefreshToken을 얻기 위한 객체입니다.
 * @param secret JWT 토큰을 발급 / 유효성검사할 때 사용할 비밀키 .yml에 저장 중
 * @return Refresh 토큰이 유효해 새 토큰을 발급하는데 성공할 경우 상태코드 200과 함께 새 토큰을 반환
 * @return Refresh 토큰이 만료될 경우 상태코드 400과 함께 로그인창으로 이동할 redirect-url을 반환
 */
package com.hongchelin.Service.JWT;

import com.hongchelin.Domain.Token;
import com.hongchelin.Repository.TokenRepository;
import com.hongchelin.dto.user.MemberDTO;
import com.hongchelin.dto.user.ResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class JWTExpiredService {
    private final JWTFilter jwtFilter;
    private final TokenRepository tokenRepository;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JWTExpiredService(JWTFilter jwtFilter, TokenRepository tokenRepository, JdbcTemplate jdbcTemplate) {
        this.tokenRepository = tokenRepository;
        this.jwtFilter = jwtFilter;
        this.jdbcTemplate = jdbcTemplate;
    }

    public ResponseEntity<ResponseDTO> getJWTRefresh(HttpServletRequest request, String secret) {
        String refreshToken = request.getHeader("Refresh-Token");
        ResponseDTO refreshTokenValidityResult = jwtFilter.checkValidityAndReturnUserRoles(secret, refreshToken);
        boolean refreshTokenValidity = refreshTokenValidityResult.getValidity();
        System.out.println(refreshTokenValidityResult);

        if (refreshTokenValidity) { //토큰 유효 / 새 AccessToken 발급
            String userId = jdbcTemplate.queryForObject("SELECT user_Id FROM TOKEN WHERE refresh_token = ? ", String.class, refreshToken);

            ResponseDTO userDataResult = jwtFilter.getUserInfo(secret, refreshToken);
            MemberDTO userData = userDataResult.getMemberInfo();
            String identifier = userData.getIdentifier();
            String newAcessToken = jwtFilter.createToken(secret, identifier);

            Token token = Token.builder()
                    .userId(userId)
                    .refreshToken(newAcessToken).build();

            tokenRepository.update(token);

            ResponseDTO responseDTO = ResponseDTO.builder()
                    .status(200)
                    .message("성공")
                    .accessToken(newAcessToken).build();

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(responseDTO);

        } else { //토큰만료
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .status(401)
                    .message("JWT Refresh Token Expired")
                    .redirectUrl("/login")
                    .build();

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(responseDTO);
        }
    }
}
