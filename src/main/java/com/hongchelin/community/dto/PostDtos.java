//package com.hongchelin.community.dto;
//
//import com.hongchelin.community.entity.Post;
//import jakarta.validation.constraints.*;
//
//import java.time.ZoneId;
//import java.time.format.DateTimeFormatter;
//
//public class PostDtos {
//
//    // 생성/수정 요청
//    public record CreateReq(
//            @NotBlank String title,
//            @NotBlank String restaurantName,
//            @NotBlank String location,
//            @NotBlank String recommendedMenu,
//            @NotBlank String content,
//            @NotNull @Min(1) @Max(5) Integer rating,
//            String imageUrl
//    ) {}
//
//    public record UpdateReq(
//            @NotBlank String title,
//            @NotBlank String restaurantName,
//            @NotBlank String location,
//            @NotBlank String recommendedMenu,
//            @NotBlank String content,
//            @NotNull @Min(1) @Max(5) Integer rating,
//            String imageUrl
//    ) {}
//
//    // 목록 아이템
//    public record ListItem(
//            Long id,
//            String title,
//            String restaurantName,
//            String preview,            // 본문 앞부분 60자
//            String thumbnailUrl,
//            String createdAtKst,        // 표시용 (yyyy. M. d. a h:mm)
//            String authorNickname,
//            String authorProfileImageUrl,
//            String authorBadgeIconUrl
//    ) {
//        public static ListItem of(Post p) {
//            var fmt = DateTimeFormatter.ofPattern("yyyy. M. d. a h:mm")
//                    .withZone(ZoneId.of("Asia/Seoul"));
//            String preview = p.getContent().length() > 60
//                    ? p.getContent().substring(0, 60) + "..."
//                    : p.getContent();
//            return new ListItem(
//                    p.getId(), p.getTitle(), p.getRestaurantName(), preview,
//                    p.getImageUrl(),
//                    fmt.format(p.getCreatedAt().atZone(ZoneId.systemDefault())),
//                    p.getAuthorNickname(), p.getAuthorProfileImageUrl(), p.getAuthorBadgeIconUrl()
//            );
//        }
//    }
//
//    // 상세
//    public record Detail(
//            Long id,
//            String title,
//            String restaurantName,
//            String location,
//            String recommendedMenu,
//            String content,
//            Integer rating,
//            String imageUrl,
//            String createdAtKst,
//            Long authorId,
//            String authorNickname,
//            String authorProfileImageUrl,
//            String authorBadgeIconUrl
//    ) {
//        public static Detail of(Post p) {
//            var fmt = DateTimeFormatter.ofPattern("yyyy. M. d. a h:mm")
//                    .withZone(ZoneId.of("Asia/Seoul"));
//            return new Detail(
//                    p.getId(), p.getTitle(), p.getRestaurantName(), p.getLocation(),
//                    p.getRecommendedMenu(), p.getContent(), p.getRating(),
//                    p.getImageUrl(),
//                    fmt.format(p.getCreatedAt().atZone(ZoneId.systemDefault())),
//                    p.getAuthorId(), p.getAuthorNickname(),
//                    p.getAuthorProfileImageUrl(), p.getAuthorBadgeIconUrl()
//            );
//        }
//    }
//
//    // 페이지 응답(간단)
//    public record PageResp<T>(
//            int page, int size, long totalElements, int totalPages, boolean last, java.util.List<T> items) {
//        public static <T> PageResp<T> of(org.springframework.data.domain.Page<T> pg) {
//            return new PageResp<>(pg.getNumber(), pg.getSize(), pg.getTotalElements(),
//                    pg.getTotalPages(), pg.isLast(), pg.getContent());
//        }
//    }
//}


//
//package com.hongchelin.community.dto;
//
//import com.hongchelin.community.entity.Post;
//import jakarta.validation.constraints.*;
//
//import java.time.Instant;
//import java.time.LocalDateTime;
//import java.time.ZoneId;
//
//public class PostDtos {
//
//    // 생성/수정 요청
//    public record CreateReq(
//            @NotBlank String title,
//            @NotBlank String restaurantName,
//            @NotBlank String location,
//            @NotBlank String recommendedMenu,
//            @NotBlank String content,
//            @NotNull @Min(1) @Max(5) Integer rating,
//            String imageUrl
//    ) {}
//
//    public record UpdateReq(
//            @NotBlank String title,
//            @NotBlank String restaurantName,
//            @NotBlank String location,
//            @NotBlank String recommendedMenu,
//            @NotBlank String content,
//            @NotNull @Min(1) @Max(5) Integer rating,
//            String imageUrl
//    ) {}
//
//    // 목록 아이템: createdAtKst 대신 createdDate(UTC, "…Z")
//    public record ListItem(
//            Long id,
//            String title,
//            String restaurantName,
//            String preview,            // 본문 앞부분 60자
//            String thumbnailUrl,
//            Instant createdDate,        // ← 변경 포인트
//            String authorNickname,
//            String authorProfileImageUrl,
//            String authorBadgeIconUrl
//    ) {
//        public static ListItem of(Post p) {
//            String content = p.getContent() == null ? "" : p.getContent();
//            String preview = content.length() > 60 ? content.substring(0, 60) + "..." : content;
//
//            return new ListItem(
//                    p.getId(),
//                    p.getTitle(),
//                    p.getRestaurantName(),
//                    preview,
//                    p.getImageUrl(),
//                    toInstant(p.getCreatedAt()), // ← UTC Instant
//                    p.getAuthorNickname(),
//                    p.getAuthorProfileImageUrl(),
//                    p.getAuthorBadgeIconUrl()
//            );
//        }
//    }
//
//    // 상세: createdAtKst 대신 createdDate(UTC, "…Z")
//    public record Detail(
//            Long id,
//            String title,
//            String restaurantName,
//            String location,
//            String recommendedMenu,
//            String content,
//            Integer rating,
//            String imageUrl,
//            Instant createdDate,        // ← 변경 포인트
//            Long authorId,
//            String authorNickname,
//            String authorProfileImageUrl,
//            String authorBadgeIconUrl
//    ) {
//        public static Detail of(Post p) {
//            return new Detail(
//                    p.getId(),
//                    p.getTitle(),
//                    p.getRestaurantName(),
//                    p.getLocation(),
//                    p.getRecommendedMenu(),
//                    p.getContent(),
//                    p.getRating(),
//                    p.getImageUrl(),
//                    toInstant(p.getCreatedAt()), // ← UTC Instant
//                    p.getAuthorId(),
//                    p.getAuthorNickname(),
//                    p.getAuthorProfileImageUrl(),
//                    p.getAuthorBadgeIconUrl()
//            );
//        }
//    }
//
//    // 페이지 응답
//    public record PageResp<T>(
//            int page, int size, long totalElements, int totalPages, boolean last, java.util.List<T> items) {
//        public static <T> PageResp<T> of(org.springframework.data.domain.Page<T> pg) {
//            return new PageResp<>(pg.getNumber(), pg.getSize(), pg.getTotalElements(),
//                    pg.getTotalPages(), pg.isLast(), pg.getContent());
//        }
//    }
//
//    // LocalDateTime(서버 타임존) → Instant(UTC, "…Z")
//    private static Instant toInstant(LocalDateTime ldt) {
//        if (ldt == null) return null;
//        return ldt.atZone(ZoneId.systemDefault()).toInstant();
//    }
//}
// src/main/java/com/hongchelin/community/dto/PostDtos.java
package com.hongchelin.community.dto;

import com.hongchelin.community.entity.Post;
import jakarta.validation.constraints.*;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class PostDtos {

    // 생성/수정 요청
    public record CreateReq(
            @NotBlank String title,
            @NotBlank String restaurantName,
            String location,                       // 선택값
            @NotBlank String recommendedMenu,
            @NotBlank String content,
            @NotNull @Min(1) @Max(5) Integer rating,
            String imageUrl
    ) {}

    public record UpdateReq(
            @NotBlank String title,
            @NotBlank String restaurantName,
            String location,                       // 선택값
            @NotBlank String recommendedMenu,
            @NotBlank String content,
            @NotNull @Min(1) @Max(5) Integer rating,
            String imageUrl
    ) {}

    // 목록 아이템
    public record ListItem(
            Long id,
            String title,
            String restaurantName,
            String preview,
            String thumbnailUrl,
            String createdAtKst,        // 표시용
            String authorNickname,
            String authorProfileImageUrl,
            String authorBadgeIconUrl
    ) {
        public static ListItem of(Post p) {
            var fmt = DateTimeFormatter.ofPattern("yyyy. M. d. a h:mm")
                    .withZone(ZoneId.of("Asia/Seoul"));
            String preview = p.getContent().length() > 60
                    ? p.getContent().substring(0, 60) + "..."
                    : p.getContent();
            return new ListItem(
                    p.getId(), p.getTitle(), p.getRestaurantName(), preview,
                    p.getImageUrl(),
                    fmt.format(p.getCreatedDate().atZone(ZoneId.systemDefault())), // createdDate 사용
                    p.getAuthorNickname(), p.getAuthorProfileImageUrl(), p.getAuthorBadgeIconUrl()
            );
        }
    }

    // 상세
    public record Detail(
            Long id,
            String title,
            String restaurantName,
            String location,            // null 일 수 있음
            String recommendedMenu,
            String content,
            Integer rating,
            String imageUrl,
            String createdAtKst,        // 표시용
            Long authorId,
            String authorNickname,
            String authorProfileImageUrl,
            String authorBadgeIconUrl
    ) {
        public static Detail of(Post p) {
            var fmt = DateTimeFormatter.ofPattern("yyyy. M. d. a h:mm")
                    .withZone(ZoneId.of("Asia/Seoul"));
            return new Detail(
                    p.getId(), p.getTitle(), p.getRestaurantName(), p.getLocation(),
                    p.getRecommendedMenu(), p.getContent(), p.getRating(),
                    p.getImageUrl(),
                    fmt.format(p.getCreatedDate().atZone(ZoneId.systemDefault())), // ✅ createdDate 사용
                    p.getAuthorId(), p.getAuthorNickname(),
                    p.getAuthorProfileImageUrl(), p.getAuthorBadgeIconUrl()
            );
        }
    }

    // 페이지 응답(간단)
    public record PageResp<T>(
            int page, int size, long totalElements, int totalPages, boolean last, java.util.List<T> items) {
        public static <T> PageResp<T> of(org.springframework.data.domain.Page<T> pg) {
            return new PageResp<>(pg.getNumber(), pg.getSize(), pg.getTotalElements(),
                    pg.getTotalPages(), pg.isLast(), pg.getContent());
        }
    }
}

