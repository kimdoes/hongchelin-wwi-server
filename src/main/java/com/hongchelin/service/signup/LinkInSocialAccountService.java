/*
package com.hongchelin.service.signup;

import com.hongchelin.Domain.Member;
import com.hongchelin.Repository.MemberRepositoryInterface;
import com.hongchelin.dto.user.ResponseDTO;
import com.hongchelin.exceptions.UnauthorizedException;
import com.hongchelin.service.JWT.JWTFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class LinkInSocialAccountService {
    private JWTFilter jwtFilter;
    private MemberRepositoryInterface memberRepository;

    @Autowired
    public LinkInSocialAccountService(JWTFilter jwtFilter, MemberRepositoryInterface memberRepository) {
        this.memberRepository = memberRepository;
        this.jwtFilter = jwtFilter;
    }

    public ResponseEntity<ResponseDTO> checkUserInformation(String secret,
                                                            OAuth2User oauth2user,
                                                            HttpServletRequest request,
                                                            OAuth2AuthenticationToken OAuth2request) {
        String userId;

        try {
            userId = jwtFilter.getTokenFromHeader(secret, request).getMemberInfo().getIdentifier();
        } catch (Exception e){
            throw new UnauthorizedException();
        }



        String registrationId = OAuth2request.getAuthorizedClientRegistrationId();

        if (registrationId.equals("google")) {
            Member member = memberRepository.findByUserId(userId).get(0);
            String email = oauth2user.getAttribute("email");

            boolean isInDb = memberRepository.existsBySocialEmail(email);

            if (isInDb) { //DB에 있음. 중복
                throw new UnauthorizedException();
            }

            member.setSocialEmail(email);
            memberRepository.save(member);
        }


        /*
        String id;
        String userToken;
        String refreshToken;
        ResponseDTO responseDTO;

        if (registrationId.equals("google")) {
            id = oauth2user.getAttribute("email");
            userToken = jwtFilter.createToken(secret, id);
            refreshToken = jwtFilter.createRefreshToken(secret, id);
        } else if (registrationId.equals("naver")) {
            Map<String, Object> userInfo = oauth2user.getAttributes();
            Map<String, Object> claims = (Map<String, Object>) userInfo.get("response");
            id = claims.get("id").toString();

            userToken = jwtFilter.createToken(secret, id);
            refreshToken = jwtFilter.createRefreshToken(secret, id);
        } else {
            id = oauth2user.getAttribute("id").toString();
            userToken = jwtFilter.createToken(secret, id);
            refreshToken = jwtFilter.createRefreshToken(secret, id);
        }

        MemberDTO memberDTO = MemberDTO.builder()
                .identifier(id)
                .accessToken(userToken)
                .refreshToken(refreshToken)
                .build();

        if (ismember) {
            responseDTO = ResponseDTO.builder()
                    .status(200)
                    .message("로그인 성공. 외부인 계정으로 로그인")
                    .redirectUrl("/")
                    .MemberInfo(memberDTO)
                    .build();

            Member member = Member.builder()
                    .nickname("이름이름")
                    .build();

            memberRepository.save(member);
        }
        else {
            responseDTO = ResponseDTO.builder()
                    .status(250)
                    .message("로그인 성공. 회원가입창 이동")
                    .accessToken(userToken)
                    .refreshToken(refreshToken)
                    .build();
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseDTO);

         */
  //  }
//}