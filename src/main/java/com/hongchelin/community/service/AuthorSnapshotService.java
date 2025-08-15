package com.hongchelin.community.service;

import com.hongchelin.community.userread.BadgeLiteRepository;
import com.hongchelin.community.userread.UserLiteRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthorSnapshotService {
    private final UserLiteRepository users;
    private final BadgeLiteRepository badges;

    public AuthorSnapshotService(UserLiteRepository users, BadgeLiteRepository badges) {
        this.users = users; this.badges = badges;
    }

    public record Snapshot(Long id, String nickname, String profileImageUrl, String badgeIconUrl) {}

    public Snapshot get(Long userId) {
        var u = users.findById(userId).orElseThrow(() -> new IllegalArgumentException("사용자 없음: " + userId));
        String badgeIcon = null;
        if (u.getActiveBadgeId() != null) {
            badgeIcon = badges.findById(u.getActiveBadgeId()).map(b -> b.getIconPath()).orElse(null);
        }
        return new Snapshot(u.getId(), u.getNickname(), u.getProfileImagePath(), badgeIcon);
    }
}
