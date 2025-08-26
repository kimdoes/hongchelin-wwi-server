/**
 * 응답용 DTO
 * 토큰은 따로 토큰용 DTO를 만들 계획이 있습니다.
 *
 * @author dx2d2y
 *
 * @param status  응답코드
 * @param message 반환 메시티
 * @param redirectUrl 이동할 URL
 * @param accessToken 액세스토큰
 * @param refreshToken 리프레시토큰
 * @param validity 토큰의 만료여부를 파악합니다. false 일 때 만료. 기본값 null
 */
package com.hongchelin.dto.user;

import com.hongchelin.dto.StoreDTO;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ResponseDTO {
    Integer status;
    String message;
    String redirectUrl;
    String accessToken;
    String refreshToken;
    Boolean validity;

    MemberDTO MemberInfo;
    StoreDTO StoreInfo;
}
