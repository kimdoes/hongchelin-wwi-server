/**
 * 컨트롤러에서 유저가 홍익대 이메일을 보내면 이를 확인해 인증번호를 보내는 코드입니다.
 * 
 * @author dx2d2y
 * 
 * @param email 전송할 이메일의 주소
 *              
 * @return 이메일 도메인 주소가 g.hongik.ac.kr 이며, 이메일 전송에 성공할 경우 상태코드 302와 함께 인증번호를 받을 창으로 이동
 * @return 이메일 전송 중 오류가 났을 경우, 상태코드 500과 함께 오류메시지를 반환함. 메시지가 null일 경우 메시지가 null임으로 알람
 * @return 이메일 도메인 주소가 g.hongik.ac.kr이 아닐 경우 상태코드 401을 반환함
 */
package com.hongchelin.service.login.HongikUniv;

import com.hongchelin.service.Email.EmailSender;
import com.hongchelin.dto.user.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.net.URI;
import java.util.Random;

@Service
public class hongikUnivloginService {
    private final EmailSender emailSender;
    public hongikUnivloginService(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    public ResponseEntity<ResponseDTO> hongikUnivloginService(String email) throws MessagingException {
        String emailServer = email.split("@")[1];

        if (emailServer.equals("g.hongik.ac.kr")){

            Random random = new Random();
            String pwd = Integer.toString(random.nextInt(899999) + 100000);

            String sub = "홍익대학교 재학생 이메일 로그인 인증요청입니다.";
            String text = "안녕하세요.\n저희 홍익대학교 맛집 아카이빙 프로젝트 '홍슐랭'을 이용해주셔서 감사합니다.\n\n인증번호는\n"+pwd+"\n입니다.\n\n감사합니다.";
            ResponseEntity<ResponseDTO> resultsendingEmail = emailSender.EmailSenderService(email, pwd, sub, text);
            Integer code = resultsendingEmail.getBody().getStatus();

            if (code == 200) {
                return ResponseEntity
                        .status(HttpStatus.FOUND)
                        .location(URI.create("/api/login/4/email/password"))
                        .build();

            } else {
                ResponseDTO body = resultsendingEmail.getBody();
                String message = (String) body.getMessage();

                ResponseDTO responseDTO = ResponseDTO.builder()
                        .status(500)
                        .message(message)
                        .build();

                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(responseDTO);

            }
        } else {
                String message = "재학생 이메일이 아닙니다.";
                ResponseDTO responseDTO = ResponseDTO.builder()
                        .status(401)
                        .message(message)
                        .build();

                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(responseDTO);
        }
    }
}
