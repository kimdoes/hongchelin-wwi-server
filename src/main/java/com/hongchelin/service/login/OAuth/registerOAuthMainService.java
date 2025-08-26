package com.hongchelin.service.login.OAuth;

import com.hongchelin.Domain.Member;
import com.hongchelin.Repository.MemberRepositoryInterface;
import com.hongchelin.dto.user.ResponseDTO;
import com.hongchelin.exceptions.CookieNotFoundExcpetion;
import com.hongchelin.exceptions.UnauthorizedException;
import com.hongchelin.service.JWT.JWTFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class registerOAuthMainService {
    String token = null;
    boolean found;

    private final MemberRepositoryInterface memberRepository;
    private final JWTFilter jwtFilter;
    public registerOAuthMainService(MemberRepositoryInterface memberRepository,
                                    JWTFilter jwtFilter) {
        this.memberRepository = memberRepository;
        this.jwtFilter = jwtFilter;
    }

    public ResponseEntity<ResponseDTO> registerOAuth(String secret,
                                           OAuth2User oauth2user,
                                           HttpServletRequest request,
                                           OAuth2AuthenticationToken OAuth2request) {
        String registrationId = OAuth2request.getAuthorizedClientRegistrationId();
        String identifier;

        if (registrationId.equals("google")) {
            identifier = oauth2user.getAttribute("email");
        } else if (registrationId.equals("naver")) {
            Map<String, Object> identifiers = (Map<String, Object>) (oauth2user.getAttributes().get("response"));
            identifier = identifiers.get("id").toString();
        } else {
            Map<String, Object> identifiers = (Map<String, Object>) (oauth2user.getAttributes().get("response"));
             identifier = identifiers.get("id").toString();
        }


        return reachSession(secret, request, identifier);
    }



    private ResponseEntity<ResponseDTO> reachSession (String secret,
                                                      HttpServletRequest request,
                                                      String identifier) {
        Cookie[] list = request.getCookies();

        for (Cookie cookie : list) {
            if ("token".equals(cookie.getName())) {
                token = cookie.getValue();
                found = true;
                break;
            }
        }

        if (!found) {
            throw new CookieNotFoundExcpetion();
        }

        if (token == null) {
            throw new UnauthorizedException();
        }

        String userId = jwtFilter.checkValidityAndReturnUserRoles(secret, token)
                .getMemberInfo()
                .getIdentifier();

        if (!jwtFilter.checkValidityAndReturnUserRoles(secret, token).getValidity()) {
            throw new UnauthorizedException();
        }

        Member member = memberRepository.findByUserId(userId).get(0);
        member.setSocialEmail(identifier);
        memberRepository.save(member);
        ResponseDTO responseDTO = ResponseDTO.builder()
                .status(200)
                .message("성공")
                .build();

        return ResponseEntity.ok(responseDTO);
    }
}
