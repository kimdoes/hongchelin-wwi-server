package com.hongchelin.service.login.OAuth;

import com.hongchelin.Repository.MemberRepositoryInterface;
import com.hongchelin.service.JWT.JWTFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class registerOAuthService {
    private final JWTFilter jwtFilter;
    private final MemberRepositoryInterface memberRepository;
    private final loginService loginService;

    public registerOAuthService(JWTFilter jwtFilter,
                                MemberRepositoryInterface memberRepository,
                                loginService loginService) {
        this.jwtFilter = jwtFilter;
        this.memberRepository = memberRepository;
        this.loginService = loginService;
    }

    public ResponseEntity<?> registerOAuthService(int oauthprovider,
                                                  String secret,
                                                  HttpServletRequest request,
                                                  HttpServletResponse response) {
        String mode = "register";
        setSessionJWT(secret, request, response);
        return loginService.loginMethodSorting(oauthprovider, response, mode);
    }

    public void setSessionJWT(String secret,
                               HttpServletRequest request,
                               HttpServletResponse response) {

        System.out.println(request.getHeader("AccessToken"));
        String token = jwtFilter.getTokenFromHeader(secret, request).getAccessToken();
        System.out.println("token is " + token);

        Cookie cookie = new Cookie("token", token);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        response.addCookie(cookie);

        System.out.println("쿠키저장완료!");
    }
}