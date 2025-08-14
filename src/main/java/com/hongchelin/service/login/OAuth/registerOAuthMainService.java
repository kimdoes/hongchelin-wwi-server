package com.hongchelin.service.login.OAuth;

import com.hongchelin.Domain.Member;
import com.hongchelin.Repository.MemberRepositoryInterface;
import com.hongchelin.exceptions.UnauthorizedException;
import com.hongchelin.service.JWT.JWTFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

public class registerOAuthMainService {
    private final MemberRepositoryInterface memberRepository;
    private final JWTFilter jwtFilter;
    public registerOAuthMainService(MemberRepositoryInterface memberRepository,
                                    JWTFilter jwtFilter) {
        this.memberRepository = memberRepository;
        this.jwtFilter = jwtFilter;
    }

    public ResponseEntity<?> registerOAuth(String secret,
                                           OAuth2User oauth2user,
                                           HttpServletRequest request,
                                           OAuth2AuthenticationToken OAuth2request) {
        String registrationId = OAuth2request.getAuthorizedClientRegistrationId();

        if (registrationId.equals("google")) {

            String email = oauth2user.getAttribute("email");
            HttpSession session = request.getSession();

            if (session != null) {
                String token = session.getAttribute("token").toString();
                String userId = jwtFilter.getTokenFromHeader(secret, request).getMemberInfo().getIdentifier();

                Member member = memberRepository.findByUserId(userId).get(0);
                member.setSocialEmail(email);
                memberRepository.save(member);

                return ResponseEntity.ok().build();
            } else {
                throw new UnauthorizedException();
            }

        } else if (registrationId.equals("naver")) {

            Map<String, Object> identifiers = (Map<String, Object>) (oauth2user.getAttributes().get("response"));
            String identifier = identifiers.get("id").toString();

            HttpSession session = request.getSession();

            if (session != null) {
                String token = session.getAttribute("token").toString();
                String userId = jwtFilter.checkValidityAndReturnUserRoles(secret, token).getMemberInfo().getIdentifier();

                Member member = memberRepository.findByUserId(userId).get(0);
                member.setSocialEmail(identifier);
            } else {
                throw new UnauthorizedException();
            }

        } else {
            String identifier = oauth2user.getAttribute("id").toString();
            return loginhelp(secret, identifier);
        }
    }
}
