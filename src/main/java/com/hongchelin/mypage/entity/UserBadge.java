package com.hongchelin.mypage.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames={"user_id","badge_id"}))
@Getter @NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class UserBadge {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(optional=false) private User user;
    @ManyToOne(optional=false) private Badge badge;
    private LocalDateTime obtainedAt;

    public static UserBadge obtain(User user, Badge badge){
        UserBadge ub = new UserBadge(); ub.user=user; ub.badge=badge; ub.obtainedAt=LocalDateTime.now(); return ub;
    }
}
