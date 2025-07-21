/**
 * loginMethodSorting -> oauthprovider의 숫자에 따라 해당 소셜로그인에 해당하는 login으로 이동함
 */

package com.hongchelin.Service.login.OAuth;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface loginInterface {
    ResponseEntity<Map<String, Object>> loginMethodSorting(int oauthprovider);
    ResponseEntity<Map<String, Object>> googlelogin();
    ResponseEntity<Map<String, Object>> naverlogin();
    ResponseEntity<Map<String, Object>> kakaologin();
    ResponseEntity<Map<String, Object>> hongikUnivlogin();
}
