// dto/BadgeDtos.java
package com.hongchelin.mypage.dto;
import com.hongchelin.mypage.entity.*;
import java.time.LocalDateTime;

public class BadgeDtos {
    public record BadgeResp(Long id, String code, String name, String description, String iconUrl){
        public static BadgeResp of(Badge b){ return new BadgeResp(b.getId(), b.getCode(), b.getName(), b.getDescription(), b.getIconPath()); }
    }
    public record MyBadgeResp(Long id, BadgeResp badge, LocalDateTime obtainedAt){
        public static MyBadgeResp of(UserBadge ub){ return new MyBadgeResp(ub.getId(), BadgeResp.of(ub.getBadge()), ub.getObtainedAt()); }
    }
    public record ShareInfo(String nickname, String badgeName, String badgeIconUrl, String shareUrl) {}
}
