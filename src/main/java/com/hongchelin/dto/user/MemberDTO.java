/**
 * userEmail : 이메일
 * userName : 유저명 (닉네임)
 * userNumber : 유저번호 (고유번호)
 */

package com.hongchelin.dto.user;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MemberDTO {
    private String identifier; //userId
    private String accessToken;
    private String refreshToken;
}