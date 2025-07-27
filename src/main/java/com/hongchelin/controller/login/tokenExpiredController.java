/**
 * AccessToken이 만료되었을 경우 프론트엔드가 이쪽으로 연결시켜야합니다
 * 
 * @param secret JWT 토큰 발급에 사용될 비밀키
 * @param request 프론트에서 헤더에 토큰을 담을 경우 헤더에서 토큰을 가져올 때 사용될 객체
 */
package com.hongchelin.controller.login;

import com.hongchelin.Service.JWT.JWTExpiredService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class tokenExpiredController {
    private final JWTExpiredService jwtExpiredService;
    public tokenExpiredController(JWTExpiredService jwtExpiredService) {
        this.jwtExpiredService = jwtExpiredService;
    }

    @GetMapping("/api/login/expired")
    public ResponseEntity<?> loginExpired(@Value("${spring.jwt.secret}") String secret, HttpServletRequest request) {
        System.out.println(secret);
        return jwtExpiredService.getJWTRefresh(request, secret);
    }
}
