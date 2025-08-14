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

    @GetMapping("/badges")
    public List<BadgeResp> allBadges(){ return badges.allBadges().stream().map(BadgeResp::of).toList(); }

    @GetMapping("/users/me/badges")
    public List<MyBadgeResp> myBadges(@RequestHeader(value="X-USER-ID", required=false) Long userId){
        Long uid = (userId == null ? 1L : userId);
        return badges.myBadges(uid).stream().map(MyBadgeResp::of).toList();
    }

    @PostMapping("/users/me/badges/{badgeId}/obtain")
    public void obtain(@RequestHeader(value="X-USER-ID", required=false) Long userId,
                       @PathVariable Long badgeId){
        Long uid = (userId == null ? 1L : userId);
        badges.obtain(uid, badgeId);
    }

    @PutMapping("/users/me/badges/active/{badgeId}")
    public void setActiveBadge(@RequestHeader(value="X-USER-ID", required=false) Long userId,
                               @PathVariable Long badgeId){
        Long uid = (userId == null ? 1L : userId);
        badges.obtain(uid, badgeId);
        users.setActiveBadge(uid, badgeId);
    }

    @GetMapping("/users/me/badges/{badgeId}/share")
    public ShareInfo share(@RequestHeader(value="X-USER-ID", required=false) Long userId,
                           @PathVariable Long badgeId){
        Long uid = (userId == null ? 1L : userId);
        return badges.share(uid, badgeId);
    }

    // 공개 공유(로그인 불필요)
    @GetMapping("/share/badge/{token}")
    public ShareInfo openShare(@PathVariable String token){
        String[] t = token.split("-");
        return badges.share(Long.parseLong(t[0]), Long.parseLong(t[1]));
    }
}


