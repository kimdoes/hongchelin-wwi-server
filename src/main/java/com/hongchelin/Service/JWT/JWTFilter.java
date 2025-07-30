/**
 * 필터는 헤더에서 토큰을 가져오고 이를 검증하기 위해 Provider에 데이터를 넘기는 역할을 한다.
 * 서비스계층에서는 filter에 JWT토큰을 넘기고 이의 메시지를 받아서 ResponseEntity를 반환함.
 *
 * createToken / createRefreshToken -> 새로운 토큰을 만드는 역할
 * getTokenFromHeader -> 프론트엔드에서 헤더에 토큰을 보내는데 헤더에서 토큰을 얻는 코드
 * checkValidity -> 유효성 검사. 검사 후 자동으로 getUserRoles로 연결됨
 * getUserRoles -> 유저의 권한 확인
 * checkValidityInTrueFalse -> 유효성 검사 후 true / false를 반환함
 * getUserRolesInJSON -> 유저의 권한 확인. 사실 이거 필요없는데 왜 만들었지
 *
 * @author dx2d2y
 * @param secret JWT 토큰 발급 / 유효성 인증에 필요한 시크릿 키
 * @param identifire 유저 식별자 (이메일이나 고유id)
 * @param token JWT 토큰 (액세스 또는 리프레시)
 */
package com.hongchelin.Service.JWT;

import com.hongchelin.dto.user.MemberDTO;
import com.hongchelin.dto.user.ResponseDTO;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class JWTFilter {
    private final JWTProvider jwtProvider;

    public JWTFilter(JWTProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    public String createToken(String secret, String identifier) {
        return jwtProvider.createToken(secret, identifier);
    }

    public String createRefreshToken(String secret, String identifier) {
        return jwtProvider.createRefreshToken(secret, identifier);
    }

    public ResponseDTO getTokenFromHeader(String secret, HttpServletRequest request) {
        String token = request.getHeader("AccessToken");

        if (token != null) {
            return checkValidityAndReturnUserRoles(secret, token);
        } else {
            return ResponseDTO.builder()
                    .status(400)
                    .message("헤더에 AccessToken이 필요합니다.")
                    .build();
        }
    }

    public ResponseDTO checkValidityAndReturnUserRoles(String secret, String token) {
        String validityMessage = jwtProvider.validateToken(secret, token);

        if (validityMessage.equals("성공")) {
            return ResponseDTO.builder()
                    .status(200)
                    .validity(true)
                    .build();

        } else {
            return ResponseDTO.builder()
                    .status(400)
                    .message(validityMessage)
                    .validity(false)
                    .build();

        }
    }

    public ResponseDTO getUserInfo(String secret, String token) {
        Claims claims = jwtProvider.getSubject(secret, token);
        String identifier = (String) claims.get("identifier");

        MemberDTO member = MemberDTO.builder()
                .identifier(identifier)
                .build();

        ResponseDTO responseDTO = ResponseDTO.builder()
                .status(200)
                .MemberInfo(member)
                .build();

        return responseDTO;
    }

    /*
    public boolean checkValidityInTrueFalse(String secret, String token) {
        String validityMessage = jwtProvider.validateToken(secret, token);

        if (validityMessage.equals("성공")) {
            return true;
        } else {
            return false;
        }
    }
    */

    /*
    public Map<String, Object> getUserRolesInJSON(String secret, String token) {
        Claims claims = jwtProvider.getSubject(secret, token);
        String userRole = (String) claims.get("roles");
        String identifier = (String) claims.get("identifier");

        Map<String, Object> response = new HashMap<>();
        response.put("userRole", userRole);
        response.put("identifier", identifier);

        return response;
    }
     */
}