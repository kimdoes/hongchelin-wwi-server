package com.hongchelin.mypage.dto;

import com.hongchelin.mypage.entity.User;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.ZoneOffset;

public class UserDtos {

    // 내 프로필 응답 DTO
    public record MeResp(Long id, String nickname, String profileImageUrl, Long activeBadgeId) {

        public static MeResp of(User u) {
            // 원본 상대경로 (예: "/files/...png")
            String url = u.getProfileImagePath();

            // 절대 URL로 변환
            String abs = toAbsoluteUrl(url);

            // 캐시 버전 파라미터
            String ver = (u.getUpdatedAt() != null)
                    ? String.valueOf(u.getUpdatedAt().toEpochSecond(ZoneOffset.UTC))
                    : String.valueOf(System.currentTimeMillis() / 1000);

            // 최종 URL (null 안전)
            String withVersion = (abs != null) ? abs + "?v=" + ver : null;

            return new MeResp(u.getId(), u.getNickname(), withVersion, u.getActiveBadgeId());
        }

        // 절대 URL 변환 헬퍼
        private static String toAbsoluteUrl(String path) {
            if (path == null || path.isBlank()) return null;
            if (path.startsWith("http://") || path.startsWith("https://")) return path;
            return ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path(path)
                    .toUriString();
        }
    }

    // 닉네임 변경 요청 DTO
    public record NicknameReq(@NotBlank String nickname) {}

    // 커뮤니티 글 작성 시 붙일 작성자 스냅샷 DTO
    public record AuthorProfile(
            String nickname,
            String profileImageUrl,
            Long activeBadgeId,
            String activeBadgeIconUrl
    ) {}
}
