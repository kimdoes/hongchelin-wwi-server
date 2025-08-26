// controller/BadgeController.java
package com.hongchelin.mypage.controller;

<<<<<<< HEAD
import com.hongchelin.mypage.dto.BadgeDtos.*;
=======
import com.hongchelin.mypage.dto.BadgeDtos.MyBadgeResp;
>>>>>>> feature/community
import com.hongchelin.mypage.service.BadgeService;
import com.hongchelin.mypage.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//
// BadgeController.java
@RestController
@RequestMapping("/api")
public class BadgeController {
    private final BadgeService badges;
    private final UserService users;
    public BadgeController(BadgeService badges, UserService users){ this.badges = badges; this.users = users; }
    private Long uid(Long h){ return h==null?1L:h; }

    /** 내가 가진 배지 목록 */
    @GetMapping("/users/me/badges")
<<<<<<< HEAD
    public java.util.List<com.hongchelin.mypage.dto.BadgeDtos.MyBadgeResp> myBadges(
            @RequestHeader(value="X-USER-ID", required=false) Long userId){
        return badges.myBadges(uid(userId)).stream()
                .map(com.hongchelin.mypage.dto.BadgeDtos.MyBadgeResp::of).toList();
=======
    public List<MyBadgeResp> myBadges(
            @RequestHeader(value="X-USER-ID", required=false) Long userId){
        return badges.myBadges(uid(userId)).stream()
                .map(MyBadgeResp::of).toList();
>>>>>>> feature/community
    }

    /** 배지 지급(테스트/관리용) */
    @PostMapping("/users/me/badges/{badgeId}")
<<<<<<< HEAD
    public com.hongchelin.mypage.dto.BadgeDtos.MyBadgeResp grant(
            @RequestHeader(value="X-USER-ID", required=false) Long userId,
            @PathVariable Long badgeId){
        var ub = badges.grantBadge(uid(userId), badgeId);
        return com.hongchelin.mypage.dto.BadgeDtos.MyBadgeResp.of(ub);
=======
    public MyBadgeResp grant(
            @RequestHeader(value="X-USER-ID", required=false) Long userId,
            @PathVariable Long badgeId){
        var ub = badges.grantBadge(uid(userId), badgeId);
        return MyBadgeResp.of(ub);
>>>>>>> feature/community
    }
}


