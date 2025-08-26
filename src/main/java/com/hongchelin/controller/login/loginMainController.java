package com.hongchelin.controller.login;

import com.hongchelin.service.login.LoginMainService;
import com.hongchelin.dto.Request.MemberRequestDTO;
import com.hongchelin.dto.user.ResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class loginMainController {
    @Value("${spring.jwt.secret}")
    private String secret;

    private final LoginMainService loginMainService;
    public loginMainController(LoginMainService loginMainService) {
        this.loginMainService = loginMainService;
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> login(@RequestBody MemberRequestDTO memberRequestDTO) {
        return loginMainService.login(secret, memberRequestDTO);
    }
}