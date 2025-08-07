package com.hongchelin.mypage.service;

import com.hongchelin.mypage.entity.Badge;
import com.hongchelin.mypage.repository.BadgeRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BadgeService {
    private final BadgeRepository badgeRepository;

    public BadgeService(BadgeRepository badgeRepository) {
        this.badgeRepository = badgeRepository;
    }

    public List<Badge> getAllBadges() {
        return badgeRepository.findAll();
    }

    public Badge createBadge(String name, String imageUrl) {
        return badgeRepository.save(new Badge(name, imageUrl));
    }
}
