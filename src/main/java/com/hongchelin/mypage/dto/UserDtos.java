// dto/UserDtos.java
package com.hongchelin.mypage.dto;
import com.hongchelin.mypage.entity.User;
import jakarta.validation.constraints.NotBlank;

public class UserDtos {
    public record MeResp(Long id, String nickname, String profileImageUrl, Long activeBadgeId){
        public static MeResp of(User u){ return new MeResp(u.getId(), u.getNickname(), u.getProfileImagePath(), u.getActiveBadgeId()); }
    }
    public record NicknameReq(@NotBlank String nickname) {}
}

