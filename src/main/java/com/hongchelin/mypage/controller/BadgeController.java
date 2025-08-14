// controller/BadgeController.java
package com.hongchelin.mypage.controller;

import com.hongchelin.mypage.dto.BadgeDtos.*;
import com.hongchelin.mypage.service.BadgeService;
import com.hongchelin.mypage.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BadgeController {
    private final BadgeService badges; private final UserService users;
    public BadgeController(BadgeService badges, UserService users){ this.badges = badges; this.users = users; }
    private Long uid(Long header){ return header==null?1L:header; }

    /** 전체 배지 (UI에서 선택지로 사용 가능) */
    @GetMapping("/badges")
    public List<BadgeResp> allBadges(){ return badges.allBadges().stream().map(BadgeResp::of).toList(); }

    /** 내가 가진 배지 */
    @GetMapping({"/users/me/badges","/user/me/badges"})
    public List<MyBadgeResp> myBadges(@RequestHeader(value="X-USER-ID", required=false) Long userId){
        return badges.myBadges(uid(userId)).stream().map(MyBadgeResp::of).toList();
    }

    /** 대표 배지 설정 */
    @PutMapping({"/users/me/badges/active/{badgeId}","/user/me/badges/active/{badgeId}"})
    public void setActive(@RequestHeader(value="X-USER-ID", required=false) Long userId,
                          @PathVariable Long badgeId){
        users.setActiveBadge(uid(userId), badgeId);
    }
}


