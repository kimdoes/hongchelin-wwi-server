// controller/MyPostController.java
package com.hongchelin.mypage.controller;

import com.hongchelin.mypage.dto.PostDtos;
import com.hongchelin.mypage.dto.PostDtos.MyPostPage;
import com.hongchelin.mypage.service.PostService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/api/users/me/posts", "/api/user/me/posts"})
public class MyPostController {
    private final PostService posts;
    public MyPostController(PostService posts){ this.posts = posts; }
    private Long uid(Long h){ return h==null?1L:h; }

    @GetMapping
    public MyPostPage myPosts(@RequestHeader(value="X-USER-ID", required=false) Long userId,
                              @RequestParam(defaultValue="0") int page,
                              @RequestParam(defaultValue="12") int size){
        return MyPostPage.of(posts.myPosts(uid(userId), page, size));
    }

    @GetMapping("/latest")
    public PostDtos.LatestTwoResp latest(
            @RequestHeader(value = "X-USER-ID", required = false) Long userId) {
        return PostDtos.LatestTwoResp.of(posts.latestTwo(userId == null ? 1L : userId));
    }
}
