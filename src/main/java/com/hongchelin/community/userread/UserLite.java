package com.hongchelin.community.userread;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "users")
public class UserLite {
    @Id
    private Long id;
    private String nickname;
    @Column(name = "profile_image_path")
    private String profileImagePath;
    @Column(name = "active_badge_id")
    private Long activeBadgeId;
}
