/**
 * checkUserInformation -> 유저의 정보를 파악, DB에 있으면 메인화면으로 넘기고 없으면 회원가입창으로 넘긴다.
 * -> 이메일활용 (인자전달)
 *
 * 유저정보는 컨트롤러에서 @AuthenticationPrincipal 어노테이션을 활용하여 전달함
 * 유저정보를 분리하는 서비스 하나 만드는 것 필요
 */

package com.hongchelin.service.login.OAuth;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

public interface loginSuccessInterface {
    ResponseEntity<Map<String, Object>> checkUserInformation(OAuth2User oAuth2User);
}
