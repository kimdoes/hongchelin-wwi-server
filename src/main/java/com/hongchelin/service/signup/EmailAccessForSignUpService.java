package com.hongchelin.service.signup;

import com.hongchelin.Repository.PasswordRepository;
import com.hongchelin.service.Email.EmailSender;
import com.hongchelin.dto.user.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EmailAccessForSignUpService {
    private final EmailSender emailSender;
    private final PasswordRepository passwordRepository;
    public EmailAccessForSignUpService(EmailSender emailSender, PasswordRepository passwordRepository) {
        this.emailSender = emailSender;
        this.passwordRepository = passwordRepository;
    }

    public ResponseEntity<ResponseDTO> EAFSS(String email) throws Exception {
        String emailServer = email.split("@")[1];

        if (emailServer.equals("g.hongik.ac.kr")){

            Random random = new Random();
            String pwd = Integer.toString(random.nextInt(899999) + 100000);

            String sub = "홍익대학교 재학생 이메일 로그인 인증요청입니다.";
            String text = "안녕하세요.\n저희 홍익대학교 맛집 아카이빙 프로젝트 '홍슐랭'을 이용해주셔서 감사합니다.\n\n인증번호는\n"+pwd+"\n입니다.\n\n감사합니다.";
            ResponseEntity<ResponseDTO> resultsendingEmail = emailSender.EmailSenderService(email, pwd, sub, text);
            Integer code = resultsendingEmail.getBody().getStatus();

            passwordRepository.save(pwd);
            System.out.println("이메일주소" + email + " : " + pwd);

            if (code == 200) {
                ResponseDTO responseDTO = ResponseDTO.builder()
                        .status(200)
                        .message("이메일 발송에 성공하였습니다.")
                        .build();

                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(responseDTO);
            } else {
                ResponseDTO body = resultsendingEmail.getBody();
                String message = body.getMessage();

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
