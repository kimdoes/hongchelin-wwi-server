package com.hongchelin.mypage.controller;

import com.hongchelin.mypage.dto.BadgeDtos;
import com.hongchelin.mypage.dto.UserDtos;
import com.hongchelin.mypage.service.BadgeService;
import com.hongchelin.mypage.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/api/users/me", "/api/user/me"}) // 임시 호환
public class UserBadgeController {

    private final UserService users;

    public UserBadgeController(UserService users) {
        this.users = users;
    }

    /** userId가 없으면 기본 1번 유저 */
    private Long uid(Long h) {
        return h == null ? 1L : h;
    }

    /** 내가 가진 배지 목록 (UI에서 선택용) */
    @GetMapping("/badges")
    public List<BadgeDtos.MyBadgeResp> myBadges(
            @RequestHeader(value = "X-USER-ID", required = false) Long userId,
            BadgeService badgeService) {
        return badgeService.myBadges(uid(userId)).stream()
                .map(BadgeDtos.MyBadgeResp::of)
                .toList();
    }

    /** 대표 배지 설정 */
    @PutMapping("/active-badge/{badgeId}")
    public UserDtos.MeResp setActiveBadge(
            @RequestHeader(value = "X-USER-ID", required = false) Long userId,
            @PathVariable Long badgeId) {
        Long id = uid(userId);
        users.setActiveBadge(id, badgeId);
        return UserDtos.MeResp.of(users.getOrCreate(id));
    }
}
