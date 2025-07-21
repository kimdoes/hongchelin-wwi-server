/**
 * 소셜로그인 기능에서 로그인에 성공할 경우 연결되는 url의 컨트롤러
 * 
 * @author dx2d2y
 * 
 * @param secret JWT 토큰 발급을 위한 비밀키
 * @param oauth2user 유저의 정보가 들어있는 객체
 * @param request 구글 / 네이버 / 카카오 로그인을 식별하기 위해 사용되는 객체
 *                
 * @return 회원가입한 적이 있으면 상태코드 200 반환, 없으면 상태코드 250 반환
 */

package com.hongchelin.controller.login.OAuth;

import com.hongchelin.Service.login.OAuth.loginSuccessService;
import com.hongchelin.dto.user.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class loginSuccessController {
    private loginSuccessService loginSuccessService;

    @Autowired
    public loginSuccessController(loginSuccessService loginSuccessService) {
        this.loginSuccessService = loginSuccessService;
    }

    @GetMapping("/api/login/success")
    public ResponseEntity<ResponseDTO> lsController(@Value("${spring.jwt.secret}") String secret, @AuthenticationPrincipal OAuth2User oauth2user, OAuth2AuthenticationToken request) {
        System.out.println(secret);
        String email = oauth2user.getAttribute("email");
        System.out.println(email);
        return loginSuccessService.checkUserInformation(secret, oauth2user, request);
    }
}