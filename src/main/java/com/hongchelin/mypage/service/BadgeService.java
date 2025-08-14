// service/BadgeService.java
package com.hongchelin.mypage.service;

import com.hongchelin.mypage.entity.*;
import com.hongchelin.mypage.dto.BadgeDtos.ShareInfo;
import com.hongchelin.mypage.exception.ApiException;
import com.hongchelin.mypage.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BadgeService {
    private final BadgeRepository badges; private final UserBadgeRepository userBadges; private final UserService users;
    public BadgeService(BadgeRepository badges, UserBadgeRepository userBadges, UserService users){
        this.badges = badges; this.userBadges = userBadges; this.users = users;
    }

    public List<Badge> allBadges(){ return badges.findAll(); }
    public List<UserBadge> myBadges(Long userId){ return userBadges.findByUser(users.getOrCreate(userId)); }

    public void obtain(Long userId, Long badgeId){
        if (userBadges.existsByUserIdAndBadgeId(userId, badgeId)) return;
        Badge b = badges.findById(badgeId).orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "배지 없음"));
        userBadges.save(UserBadge.obtain(users.getOrCreate(userId), b));
    }

    public ShareInfo share(Long userId, Long badgeId){
        if (!userBadges.existsByUserIdAndBadgeId(userId, badgeId))
            throw new ApiException(HttpStatus.FORBIDDEN, "보유하지 않은 배지");
        User u = users.getOrCreate(userId);
        Badge b = badges.findById(badgeId).orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "배지 없음"));
        String token = userId + "-" + badgeId;
        return new ShareInfo(u.getNickname(), b.getName(), b.getIconPath(), "/api/share/badge/" + token);
    }
}

