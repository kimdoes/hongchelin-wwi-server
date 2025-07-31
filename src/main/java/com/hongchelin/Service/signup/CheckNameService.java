package com.hongchelin.Service.signup;

import com.hongchelin.Domain.Member;
import com.hongchelin.Repository.MemberRepository;
import com.hongchelin.dto.user.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CheckNameService {
    private final MemberRepository memberRepository;
    public CheckNameService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public ResponseEntity<ResponseDTO> CheckName(String name) {
        Member member = new Member();
        member.setNickname(name);
        boolean checkName = memberRepository.checkingName(member);

        if (checkName) {
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .status(200)
                    .message("중복되지 않습니다.")
                    .build();

            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } else {
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .status(401)
                    .message("동일한 아이디가 이미 있습니다.")
                    .build();

            return new ResponseEntity<>(responseDTO, HttpStatus.UNAUTHORIZED);
        }
    }
}
