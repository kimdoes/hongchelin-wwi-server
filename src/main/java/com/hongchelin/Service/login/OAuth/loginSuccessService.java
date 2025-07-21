/**
 * OAuth 로그인이 성공한 경우 후속조치를 취하는 코드
 *
 * @author dx2d2y
 *
 * 다음으로 이동해야할 것
 * response == 200  ->  main
 * response == 201  ->  회원가입창
 *
 *
 * @param ismember - true -> DB에 userEmail이 조회됨 / 반대는 false
 * @param response -> 응답코드
 *
 * @return 유저의 이메일(구글로그인) 또는 id(네이버 / 카카오로그인)과 권한(소셜로그인은 outer)을 부여하여 토큰 생성
 *         ismember가 true일 경우 로그인 성공, 메인화면 이동 (상태코드 200)
 *         false일 경우 회원가입 창으로 이동 (상태코드 250. 추후 회원가입 기능이 완료되면 302로 수정)
 */

package com.hongchelin.Service.login.OAuth;

import com.hongchelin.Service.JWT.JWTFilter;
import com.hongchelin.dto.user.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class loginSuccessService {
    @Autowired
    private JWTFilter jwtFilter;
    public loginSuccessService(JWTFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    public ResponseEntity<ResponseDTO> checkUserInformation(String secret, OAuth2User oauth2user, OAuth2AuthenticationToken request) {
        boolean ismember = true;
        String registrationId = request.getAuthorizedClientRegistrationId();
        // DB와 대조하는 프로그램 작성 필요

        String email;
        String userToken;
        String refreshToken;
        String roles = "outer";
        ResponseDTO responseDTO;

        if (registrationId.equals("google")) {
            email = oauth2user.getAttribute("email");
            userToken = jwtFilter.createToken(secret, email, roles);
            refreshToken = jwtFilter.createRefreshToken(secret, email, roles);
        } else if (registrationId.equals("naver")) {
            Map<String, Object> userInfo = oauth2user.getAttributes();
            Map<String, Object> claims = (Map<String, Object>) userInfo.get("response");
            String id = claims.get("id").toString();

            userToken = jwtFilter.createToken(secret, id, roles);
            refreshToken = jwtFilter.createRefreshToken(secret, id, roles);
        } else {
            String id = oauth2user.getAttribute("id").toString();
            userToken = jwtFilter.createToken(secret, id, roles);
            refreshToken = jwtFilter.createRefreshToken(secret, id, roles);
        }

        if (ismember) {
            responseDTO = ResponseDTO.builder()
                    .status(200)
                    .message("로그인 성공. 외부인 계정으로 로그인")
                    .redirectUrl("/")
                    .accessToken(userToken)
                    .refreshToken(refreshToken)
                    .build();
        }
        else {
            responseDTO = ResponseDTO.builder()
                    .status(250)
                    .message("로그인 성공. 회원가입창 이동")
                    .accessToken(userToken)
                    .refreshToken(refreshToken)
                    .build();
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseDTO);
    }
}