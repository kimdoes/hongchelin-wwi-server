/**
 * 필터는 헤더에서 토큰을 가져오고 이를 검증하기 위해 Provider에 데이터를 넘기는 역할을 한다.
 * 서비스계층에서는 filter에 JWT토큰을 넘기고 이의 메시지를 받아서 ResponseEntity를 반환함.
 *
 * getTokenFromHeader -> 헤더에 있는 AccessToken을 가져온다.
 */
package com.hongchelin.Service.JWT;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface JWTFilterInterface {
    ResponseEntity<?> getTokenFromHeader(String header, HttpServletRequest request);
    ResponseEntity<?> checkValidity(String token);
    ResponseEntity<?> getUserRoles(String token);
}
