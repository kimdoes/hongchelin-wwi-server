package com.hongchelin.community.dto;

import com.hongchelin.community.entity.Comment;
import jakarta.validation.constraints.NotBlank;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class CommentDtos {

    public record CreateReq(@NotBlank String content) {}

    public record Item(Long id, String content, String createdAtKst,
                       Long authorId, String authorNickname, String authorProfileImageUrl) {
        public static Item of(Comment c) {
            var fmt = DateTimeFormatter.ofPattern("yyyy. M. d. a h:mm")
                    .withZone(ZoneId.of("Asia/Seoul"));
            return new Item(c.getId(), c.getContent(),
                    fmt.format(c.getCreatedAt().atZone(ZoneId.systemDefault())),
                    c.getAuthorId(), c.getAuthorNickname(), c.getAuthorProfileImageUrl());
        }
    }

    public record ListResp(java.util.List<Item> items) {
        public static ListResp of(java.util.List<Comment> list) {
            return new ListResp(list.stream().map(Item::of).toList());
        }
    }
}
