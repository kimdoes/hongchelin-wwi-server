/**
 * 로그인 기능
 * url 주소에 있는 authprovider에 따라 다른 로그인 기능으로 이어짐
 * 1 - 구글 / 2 - 네이버 / 3 - 카카오 / 4 - 홍익대 재학생 / 이외 - 오류
 *
 * @author dx2d2y
 *
 * @param authprovider 로그인 방법에 대한 구분 제공
 *
 * @return loginMethodSorting 에서는 각 로그인에 맞는 메서드로 연결됨 / authprovider가 규격 외일 경우 상태코드 400을 반환
 * @return 나머지 메서드들은 상태코드 302와 함께 각 로그인에 맞는 url로 리다이렉트 시킴
 */

package com.hongchelin.Service.login.OAuth;

import com.hongchelin.dto.user.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Map;

@Service
public class loginService {
    public ResponseEntity<?> loginMethodSorting(int oauthprovider) {
        switch (oauthprovider) {
            case 1:
                return googlelogin();
            case 2:
                return naverlogin();
            case 3:
                return kakaologin();
            case 4:
                return hongikUnivlogin();
            default:
                ResponseDTO responseDTO = ResponseDTO.builder()
                        .status(400)
                        .message("잘못된 authprovider가 부여되었습니다.")
                        .build();

                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(responseDTO);
        }
    }

    public ResponseEntity<Map<String, Object>> googlelogin() {
        /*
        String googleUrl = "https://accounts.google.com/o/oauth2/v2/auth" +
                "?client_id=892115859352-j4q9rqsopdidoppfvbg86kmi5n8409cb.apps.googleusercontent.com" +
                "&redirect_uri=http://localhost:8080/login/oauth2/code/google" +
                "&response_type=code" +
                "&access_type=offline" +
                "&prompt=consent" +
                "&scope=openid%20profile%20email";

        Map<String, Object> data = new HashMap<>();
        data.put("code", 200);
        data.put("message", "성공");
        data.put("redirect_uri", googleUrl);
         */

        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create("/oauth2/authorization/google"))
                .build();
    }

    public ResponseEntity<Map<String, Object>> naverlogin() {
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create("/oauth2/authorization/naver"))
                .build();
    }

    public ResponseEntity<Map<String, Object>> kakaologin() {
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create("/oauth2/authorization/kakao"))
                .build();
    }

    public ResponseEntity<Map<String, Object>> hongikUnivlogin() {
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create("/api/login/4/hongikUniv"))
                .build();
    }
}