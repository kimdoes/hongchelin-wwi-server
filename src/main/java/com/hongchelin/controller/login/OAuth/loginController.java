/**
 * 로그인 요청이 들어왔을 때 사용할 컨트롤러
 * 
 * @author dx2d2y
 * @param authprovider 로그인 식별자 (1 - 구글 / 2 - 네이버 / 3 - 카카오 / 4 - 재학생 / 이외 - 오류)
 * @return 성공 시 상태코드 302와 함께 자동 리다이렉트 / authprovider가 잘못될 경우 상태코드 400 반환
 */
package com.hongchelin.controller.login.OAuth;

import com.hongchelin.Service.login.OAuth.loginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
public class loginController {
    private final loginService loginService;

    @Autowired
    public loginController(loginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/{authprovider}")
    public ResponseEntity<?> login(@PathVariable int authprovider) {
        return loginService.loginMethodSorting(authprovider);
    }
}
