/**
 * OAuth 로그인이 성공한 경우 후속조치를 취하는 코드
 *
 * @author dx2d2y
 *
 * 다음으로 이동해야할 것
 * response == 200  ->  main
 * response == 201  ->  회원가입창
 *
 *
 * @param ismember - true -> DB에 userEmail이 조회됨 / 반대는 false
 * @param response -> 응답코드
 *
 * @return 유저의 이메일(구글로그인) 또는 id(네이버 / 카카오로그인)과 권한(소셜로그인은 outer)을 부여하여 토큰 생성
 *         ismember가 true일 경우 로그인 성공, 메인화면 이동 (상태코드 200)
 *         false일 경우 회원가입 창으로 이동 (상태코드 250. 추후 회원가입 기능이 완료되면 302로 수정)
 */

package com.hongchelin.service.login.OAuth;

import com.hongchelin.Domain.Member;
import com.hongchelin.Repository.MemberRepositoryInterface;
import com.hongchelin.Repository.TokenRepositoryInterface;
import com.hongchelin.dto.Request.MemberRequestDTO;
import com.hongchelin.exceptions.UnauthorizedException;
import com.hongchelin.service.JWT.JWTFilter;
import com.hongchelin.dto.user.MemberDTO;
import com.hongchelin.dto.user.ResponseDTO;
import com.hongchelin.service.login.LoginMainService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class loginSuccessService extends LoginMainService {
    private final TokenRepositoryInterface tokenRepositoryInterface;
    private JWTFilter jwtFilter;
    private MemberRepositoryInterface memberRepository;

    @Autowired
    public loginSuccessService(JWTFilter jwtFilter,
                               MemberRepositoryInterface memberRepository,
                               TokenRepositoryInterface tokenRepositoryInterface) {

        super(memberRepository, jwtFilter, tokenRepositoryInterface);
        this.tokenRepositoryInterface = tokenRepositoryInterface;
    }

    public ResponseEntity<ResponseDTO> checkUserInformation(String secret,
                                                            OAuth2User oauth2user,
                                                            HttpServletRequest request,
                                                            OAuth2AuthenticationToken OAuth2request) {
        String registrationId = OAuth2request.getAuthorizedClientRegistrationId();

        if (registrationId.equals("google")) {

            String email = oauth2user.getAttribute("email");
            return loginhelp(secret, email);

        } else if (registrationId.equals("naver")) {
            Map<String, Object> identifiers = (Map<String, Object>) (oauth2user.getAttributes().get("response"));
            String identifier = identifiers.get("id").toString();

            return loginhelp(secret, identifier);
        } else {
            String identifier = oauth2user.getAttribute("id").toString();
            return loginhelp(secret, identifier);
        }
    }

    public ResponseEntity<ResponseDTO> loginhelp(String secret, String identifier) {
        Optional<Member> memberOptional = memberRepository.findBySocialEmail(identifier);

        if (memberOptional.isPresent()) {
            MemberRequestDTO memberRequestDTO = MemberRequestDTO.builder()
                    .userId(memberOptional.get().getUserId())
                    .password(memberOptional.get().getPassword())
                    .build();

            return login(secret, memberRequestDTO);
        } else {
            throw new UnauthorizedException();
        }
    }
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


    }

}
 */