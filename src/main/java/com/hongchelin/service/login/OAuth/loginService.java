/**
 * 로그인 기능
 * url 주소에 있는 authprovider에 따라 다른 로그인 기능으로 이어짐
 * 1 - 구글 / 2 - 네이버 / 3 - 카카오 / 4 - 홍익대 재학생 / 이외 - 오류
 *
 * @author dx2d2y
 *
 * @param authprovider 로그인 방법에 대한 구분 제공
 *
 * @return loginMethodSorting 에서는 각 로그인에 맞는 메서드로 연결됨 / authprovider가 규격 외일 경우 상태코드 400을 반환
 * @return 나머지 메서드들은 상태코드 302와 함께 각 로그인에 맞는 url로 리다이렉트 시킴
 */

package com.hongchelin.service.login.OAuth;

import com.hongchelin.Repository.MemberRepositoryInterface;
import com.hongchelin.dto.user.ResponseDTO;
import com.hongchelin.service.JWT.JWTFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
public class loginService {
    protected final MemberRepositoryInterface memberRepository;
    protected final JWTFilter jwtFilter;

        public loginService(JWTFilter jwtFilter,
                                MemberRepositoryInterface memberRepository) {
        this.jwtFilter = jwtFilter;
        this.memberRepository = memberRepository;
    }

    public ResponseEntity<?> loginMethodSorting(int oauthprovider,
                                                HttpServletResponse response,
                                                String mode) {
        setCookie(mode, response);
        switch (oauthprovider) {
            case 1:
                return googlelogin(response);
            case 2:
                return naverlogin(response);
            case 3:
                return kakaologin(response);
            default:
                ResponseDTO responseDTO = ResponseDTO.builder()
                        .status(400)
                        .message("잘못된 authprovider가 부여되었습니다.")
                        .build();

                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(responseDTO);
        }
    }

    public ResponseEntity<?> googlelogin(HttpServletResponse response) {
            String url = "/oauth2/authorization/google";

        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create(url))
                .build();
    }

    public ResponseEntity<?> naverlogin(HttpServletResponse response) {

            String url = "/oauth2/authorization/naver";
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create(url))
                .build();
    }

    public ResponseEntity<?> kakaologin(HttpServletResponse response) {
            String url = "/oauth2/authorization/kakao";

            return ResponseEntity
                    .status(HttpStatus.FOUND)
                    .location(URI.create(url))
                    .build();
    }

    public void setCookie(String mode, HttpServletResponse response) {
        Cookie cookie = new Cookie("mode", mode);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge(3600);
        response.addCookie(cookie);

        System.out.println("로그인");
    }
}