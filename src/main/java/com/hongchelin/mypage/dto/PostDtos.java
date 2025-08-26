// dto/PostDtos.java
package com.hongchelin.mypage.dto;
<<<<<<< HEAD
import com.hongchelin.mypage.entity.Post;
import org.springframework.data.domain.Page;
=======

import com.hongchelin.mypage.entity.Post;
import org.springframework.data.domain.Page;

>>>>>>> feature/community
import java.util.List;

public class PostDtos {
    public record MyPostItem(Long id, String title, String thumbnailUrl){
        public static MyPostItem of(Post p){ return new MyPostItem(p.getId(), p.getTitle(), p.getImagePath()); }
    }
    public record MyPostPage(int page, int size, long total, boolean empty, List<MyPostItem> items){
        public static MyPostPage of(Page<Post> pg) {
            return new MyPostPage(pg.getNumber(), pg.getSize(), pg.getTotalElements(), pg.getTotalElements() == 0,
                    pg.getContent().stream().map(MyPostItem::of).toList());
        }
    }

<<<<<<< HEAD
    public record LatestTwoResp(boolean empty, java.util.List<MyPostItem> items) {
        public static LatestTwoResp of(java.util.List<Post> list) {
=======
    public record LatestTwoResp(boolean empty, List<MyPostItem> items) {
        public static LatestTwoResp of(List<Post> list) {
>>>>>>> feature/community
            return new LatestTwoResp(list.isEmpty(),
                    list.stream().map(MyPostItem::of).toList());
        }
    }
}

