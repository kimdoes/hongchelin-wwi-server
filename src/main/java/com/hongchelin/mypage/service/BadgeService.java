// service/BadgeService.java
package com.hongchelin.mypage.service;

import com.hongchelin.mypage.entity.*;
import com.hongchelin.mypage.dto.BadgeDtos.BadgeResp;
import com.hongchelin.mypage.repository.*;
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

    /** (선택) 필요시 다른 배지를 지급하려면 사용 */
    public void obtain(Long userId, Long badgeId){
        if (userBadges.existsByUserIdAndBadgeId(userId, badgeId)) return;
        Badge b = badges.findById(badgeId).orElseThrow();
        userBadges.save(UserBadge.obtain(users.getOrCreate(userId), b));
    }

    public BadgeResp activeBadgeInfo(Long userId){
        User u = users.getOrCreate(userId);
        if (u.getActiveBadgeId()==null) return null;
        return badges.findById(u.getActiveBadgeId()).map(BadgeResp::of).orElse(null);
    }
}


