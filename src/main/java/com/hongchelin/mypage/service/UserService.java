// service/UserService.java
package com.hongchelin.mypage.service;

import com.hongchelin.mypage.dto.UserDtos.AuthorProfile;
import com.hongchelin.mypage.entity.*;
import com.hongchelin.mypage.exception.ApiException;
import com.hongchelin.mypage.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @Transactional
public class UserService {
    private final UserRepository users;
    private final BadgeRepository badges;
    private final UserBadgeRepository userBadges;

    private static final String DEFAULT_BADGE_CODE = "WELCOME";

    public UserService(UserRepository users, BadgeRepository badges, UserBadgeRepository userBadges){
        this.users = users; this.badges = badges; this.userBadges = userBadges;
    }

    /** 없으면 자동 생성 + 기본 배지 자동 지급/대표 설정 */
    public User getOrCreate(Long id){
        return users.findById(id).orElseGet(() -> {
            User u = users.save(User.createWithId(id, "user"+id));
            Badge welcome = badges.findByCode(DEFAULT_BADGE_CODE)
                    .orElseGet(() -> badges.save(Badge.create(DEFAULT_BADGE_CODE, "환영 배지", "가입 환영!", "/files/badges/welcome.png")));
            userBadges.save(UserBadge.obtain(u, welcome));
            u.setActiveBadge(welcome.getId());
            return u;
        });
    }

    public User changeNickname(Long userId, String nickname){
        if (nickname==null || nickname.isBlank() || nickname.length()>30)
            throw new ApiException(HttpStatus.BAD_REQUEST, "닉네임은 1~30자");
        User u = getOrCreate(userId); u.changeNickname(nickname); return u;
    }

    public User changeProfileImage(Long userId, String url){
        User u = getOrCreate(userId); u.changeProfileImage(url); return u;
    }

    public void setActiveBadge(Long userId, Long badgeId){
        if (!userBadges.existsByUserIdAndBadgeId(userId, badgeId))
            throw new ApiException(HttpStatus.FORBIDDEN, "보유하지 않은 배지");
        getOrCreate(userId).setActiveBadge(badgeId);
    }

    /** 커뮤니티 글 작성 시 사용할 작성자 스냅샷 */
    public AuthorProfile authorProfile(Long userId){
        User u = getOrCreate(userId);
        String icon = null;
        if (u.getActiveBadgeId()!=null) {
            icon = badges.findById(u.getActiveBadgeId()).map(Badge::getIconPath).orElse(null);
        }
        return new AuthorProfile(u.getNickname(), u.getProfileImagePath(), u.getActiveBadgeId(), icon);
    }

    public User getOrThrow(Long id){
        return users.findById(id).orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "사용자 없음"));
    }
}

