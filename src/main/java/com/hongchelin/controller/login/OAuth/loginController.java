/**
 * 로그인 요청이 들어왔을 때 사용할 컨트롤러
 * 
 * @author dx2d2y
 * @param authprovider 로그인 식별자 (1 - 구글 / 2 - 네이버 / 3 - 카카오 / 4 - 재학생 / 이외 - 오류)
 * @return 성공 시 상태코드 302와 함께 자동 리다이렉트 / authprovider가 잘못될 경우 상태코드 400 반환
 */
package com.hongchelin.controller.login.OAuth;

import com.hongchelin.dto.user.ResponseDTO;
import com.hongchelin.service.login.OAuth.loginService;
import com.hongchelin.service.login.OAuth.loginSuccessService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class loginController {
    private final loginService loginService;
    private final loginSuccessService loginSuccessService;


    @Autowired
    public loginController(loginService loginService,
                           loginSuccessService loginSuccessService) {
        this.loginService = loginService;
        this.loginSuccessService = loginSuccessService;
    }

    @GetMapping("/{authprovider}")
    public ResponseEntity<?> login(@PathVariable int authprovider,
                                   HttpServletResponse response) {
        String mode = "login";
        return loginService.loginMethodSorting(authprovider, response, mode);
    }

    @GetMapping("/success")
    public ResponseEntity<ResponseDTO> lsController(
            @Value("${spring.jwt.secret}") String secret,
            @AuthenticationPrincipal OAuth2User oauth2user,
            OAuth2AuthenticationToken OAuth2request,
            HttpServletRequest request) {

        System.out.println(secret);
        String email = oauth2user.getAttribute("email");
        System.out.println(email);
        return loginSuccessService.checkUserInformation(secret, oauth2user, request, OAuth2request);
    }
}
