// dto/PostDtos.java
package com.hongchelin.mypage.dto;
import com.hongchelin.mypage.entity.Post;
import org.springframework.data.domain.Page;
import java.util.List;

public class PostDtos {
    public record MyPostItem(Long id, String title, String thumbnailUrl){
        public static MyPostItem of(Post p){ return new MyPostItem(p.getId(), p.getTitle(), p.getImagePath()); }
    }
    public record MyPostPage(int page, int size, long total, List<MyPostItem> items){
        public static MyPostPage of(Page<Post> pg){
            return new MyPostPage(pg.getNumber(), pg.getSize(), pg.getTotalElements(),
                    pg.getContent().stream().map(MyPostItem::of).toList());
        }
    }
}

