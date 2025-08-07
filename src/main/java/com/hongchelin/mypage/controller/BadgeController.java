package com.hongchelin.mypage.controller;

import com.hongchelin.mypage.entity.Badge;
import com.hongchelin.mypage.service.BadgeService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/badges")
public class BadgeController {

    private final BadgeService badgeService;

    public BadgeController(BadgeService badgeService) {
        this.badgeService = badgeService;
    }

    @GetMapping
    public List<Badge> getAllBadges() {
        return badgeService.getAllBadges();
    }

    @PostMapping
    public Badge createBadge(@RequestParam String name, @RequestParam String imageUrl) {
        return badgeService.createBadge(name, imageUrl);
    }
}
