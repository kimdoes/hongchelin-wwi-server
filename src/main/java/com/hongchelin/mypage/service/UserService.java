// src/main/java/com/hongchelin/mypage/service/UserService.java
package com.hongchelin.mypage.service;

import com.hongchelin.mypage.dto.UserDtos.AuthorProfile;
import com.hongchelin.mypage.entity.Badge;
import com.hongchelin.mypage.entity.User;
import com.hongchelin.mypage.entity.UserBadge;
import com.hongchelin.mypage.exception.ApiException;
import com.hongchelin.mypage.repository.BadgeRepository;
import com.hongchelin.mypage.repository.UserBadgeRepository;
import com.hongchelin.mypage.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    private final UserRepository users;
    private final BadgeRepository badges;
    private final UserBadgeRepository userBadges;

    private static final String DEFAULT_BADGE_CODE = "WELCOME";
    // 가입 시 자동 지급할 기본 6개 배지
    private static final String[] DEFAULT_BADGE_CODES = {
            "WELCOME", "KOREAN", "JAPANESE", "WESTERN", "CHINESE", "CAFE"
    };

    public UserService(UserRepository users, BadgeRepository badges, UserBadgeRepository userBadges){
        this.users = users;
        this.badges = badges;
        this.userBadges = userBadges;
    }

    /** 없으면 생성 + 기본 배지 6개 지급 + 대표(WELCOME) 설정 */
    public User getOrCreate(Long id){
        return users.findById(id).orElseGet(() -> {
            // 1) 유저 생성
            User u = users.save(User.createWithId(id, "user" + id));

            // 2) 기본 배지 6개 보장 + 모두 지급 (idempotent)
            Long firstBadgeId = null;
            for (String code : DEFAULT_BADGE_CODES) {
                Badge b = badges.findByCode(code).orElseGet(() ->
                        badges.save(Badge.create(code, nameByCode(code), descByCode(code), iconByCode(code)))
                );
                if (!userBadges.existsByUserIdAndBadgeId(u.getId(), b.getId())) {
                    userBadges.save(UserBadge.obtain(u, b));
                }
                if (firstBadgeId == null) firstBadgeId = b.getId();
            }

            // 3) 대표 배지 설정: WELCOME 우선, 없으면 첫 배지
            Long activeId = badges.findByCode(DEFAULT_BADGE_CODE)
                    .map(Badge::getId)
                    .orElse(firstBadgeId);
            u.setActiveBadge(activeId);

            return u;
        });
    }

    /** 닉네임 변경 */
    public User changeNickname(Long userId, String nickname){
        if (nickname == null || nickname.isBlank() || nickname.length() > 30)
            throw new ApiException(HttpStatus.BAD_REQUEST, "닉네임은 1~30자");
        User u = getOrCreate(userId);
        u.changeNickname(nickname);
        return u;
    }

    /** 프로필 이미지 변경 */
    public User changeProfileImage(Long userId, String url){
        User u = getOrCreate(userId);
        u.changeProfileImage(url);
        return u;
    }

    /** 대표 배지 설정 (보유 검증) */
    public void setActiveBadge(Long userId, Long badgeId){
        if (!userBadges.existsByUserIdAndBadgeId(userId, badgeId))
            throw new ApiException(HttpStatus.FORBIDDEN, "보유하지 않은 배지");
        getOrCreate(userId).setActiveBadge(badgeId);
    }

    /** 커뮤니티에서 사용할 작성자 스냅샷 */
    public AuthorProfile authorProfile(Long userId){
        User u = getOrCreate(userId);
        String icon = null;
        if (u.getActiveBadgeId() != null) {
            icon = badges.findById(u.getActiveBadgeId()).map(Badge::getIconPath).orElse(null);
        }
        return new AuthorProfile(
                u.getNickname(),
                u.getProfileImagePath(),
                u.getActiveBadgeId(),
                icon
        );
    }

    public User getOrThrow(Long id){
        return users.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "사용자 없음"));
    }

    // ---- 기본 배지 메타 헬퍼 ----
    private String nameByCode(String code) {
        return switch (code) {
            case "WELCOME"  -> "환영 배지";
            case "KOREAN"   -> "한식";
            case "JAPANESE" -> "일식";
            case "WESTERN"  -> "양식";
            case "CHINESE"  -> "중식";
            case "CAFE"     -> "카페";
            default -> code;
        };
    }
    private String descByCode(String code) {
        return switch (code) {
            case "WELCOME"  -> "가입 환영!";
            case "KOREAN"   -> "한식 마스터";
            case "JAPANESE" -> "일식 마스터";
            case "WESTERN"  -> "양식 마스터";
            case "CHINESE"  -> "중식 마스터";
            case "CAFE"     -> "카페 마스터";
            default -> code + " 배지";
        };
    }
    private String iconByCode(String code) {
        // 실제 아이콘 경로에 맞게 수정하세요
        return switch (code) {
            case "WELCOME"  -> "/files/badges/welcome.png";
            case "KOREAN"   -> "/files/badges/korean.png";
            case "JAPANESE" -> "/files/badges/japanese.png";
            case "WESTERN"  -> "/files/badges/western.png";
            case "CHINESE"  -> "/files/badges/chinese.png";
            case "CAFE"     -> "/files/badges/cafe.png";
            default -> "/files/badges/" + code.toLowerCase() + ".png";
        };
    }
}
