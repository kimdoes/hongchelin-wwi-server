package com.hongchelin.service.login.OAuth;

import com.hongchelin.Repository.MemberRepositoryInterface;
import com.hongchelin.dto.whatForSignupDTO;
import com.hongchelin.service.JWT.JWTFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class registerOAuthService extends loginService {
    private final JWTFilter jwtFilter;
    private final MemberRepositoryInterface memberRepository;

    public registerOAuthService(JWTFilter jwtFilter,
                                MemberRepositoryInterface memberRepository) {
        this.jwtFilter = jwtFilter;
        this.memberRepository = memberRepository;
    }

    @Override
    public void setLoginFor() {
        whatForSignupDTO.setForLogin(false);
    }

    public void setSessionJWT(final String token,
                               String secret,
                               HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("token", token);
    }
}