/**
 * 보낼 이메일주소 (to) / 제목 (subject), 내용 (text)를 담아 이메일을 보내는 코드입니다.
 *
 * @author dx2d2y
 * @param to : 보낼 이메일 주소
 * @param pwd : 인증번호(인증번호로 로그인 기능을 사용할 경우)
 * @param subject : 메일 제목
 * @param text : 메일 내용
 *
 * @return SMTP 인증에 성공하고 이메일을 보내는데 성공하면 200 코드를 반환
 * @return SMTP 인증에 실패하거나 이메일 전송에 실패할 경우 500 코드와 함께 오류메시지를 반환
 */

package com.hongchelin.service.Email;

import com.hongchelin.dto.user.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class EmailSender {
    public ResponseEntity<ResponseDTO> EmailSenderService(String to, String pwd, String subject, String text) throws MessagingException {
       try {
           String from = "hongchelin422@gmail.com";
           String password = "prthyoacfatxvgto";
           String host = "smtp.gmail.com";

           Properties props = new Properties();
           props.put("mail.smtp.host", host);
           props.put("mail.smtp.port", "587");
           props.put("mail.smtp.auth", "true");
           props.put("mail.smtp.starttls.enable", "true");

           Session session = Session.getInstance(props, new Authenticator() {
               protected PasswordAuthentication getPasswordAuthentication() {
                   return new PasswordAuthentication(from, password);
               }
           });

           Message message = new MimeMessage(session);
           message.setFrom(new InternetAddress(from));
           message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
           message.setSubject(subject);
           message.setText(text);

           Transport.send(message);

           ResponseDTO responseDTO = ResponseDTO.builder()
                   .status(200)
                   .message("인증번호 발송에 성공하였습니다.\n"+pwd)
                   .build();

           return ResponseEntity.ok(responseDTO);
       } catch (Exception e) {
           ResponseDTO responseDTO = ResponseDTO.builder()
                   .status(500)
                   .message(e.getMessage())
                   .build();

           return ResponseEntity
                   .status(HttpStatus.INTERNAL_SERVER_ERROR)
                   .body(responseDTO);
       }
    }
}
