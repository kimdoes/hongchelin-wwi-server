/**
 * 홍대 재학생 로그인
 *
 * url로 postmapping을 받으면 body에서 이메일을 받아 그 이메일 주소로 인증번호를 보내게 됩니다.
 *
 * @author dx2d2y
 * @return 성공시 302 상태코드 및 자동 리다이렉트 / 이메일 전송 실패 시 상태코드 500 / 도메인 주소가 재학생이 아닐 경우 상태코드 401 반환
 */
package com.hongchelin.controller.login.HongikUniv;

import com.hongchelin.Service.login.HongikUniv.hongikUnivloginService;
import com.hongchelin.dto.user.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.mail.MessagingException;

@Controller
public class hongikUnivloginController {
    private final hongikUnivloginService hongikUnivloginService;
    public hongikUnivloginController(hongikUnivloginService hongikUnivloginService) {
        this.hongikUnivloginService = hongikUnivloginService;
    }

    //@PostMapping("/api/login/4/email")
    @GetMapping("/api/login/4/email")
    public ResponseEntity<ResponseDTO> hongikUnivlogin(/*@RequestBody Map<String, Object> body */) throws MessagingException {
        //String email = (String) body.get("email");
        String email = "kdhyun422@g.hongik.ac.kr";
        return hongikUnivloginService.hongikUnivloginService(email);
    }
}
