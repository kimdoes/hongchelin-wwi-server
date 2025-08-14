// dto/UserDtos.java
package com.hongchelin.mypage.dto;

import com.hongchelin.mypage.entity.User;
import jakarta.validation.constraints.NotBlank;

public class UserDtos {

    // 내 프로필 응답
    public record MeResp(Long id, String nickname, String profileImageUrl, Long activeBadgeId){
        public static MeResp of(User u){ return new MeResp(u.getId(), u.getNickname(), u.getProfileImagePath(), u.getActiveBadgeId()); }
    }

    // 닉네임 변경 요청
    public record NicknameReq(@NotBlank String nickname) {}

    // 커뮤니티 글 작성 시 붙일 "작성자 스냅샷"
    public record AuthorProfile(String nickname, String profileImageUrl, Long activeBadgeId, String activeBadgeIconUrl) {}
}

