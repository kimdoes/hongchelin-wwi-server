package com.hongchelin.mypage.controller;

import com.hongchelin.mypage.entity.User;
import com.hongchelin.mypage.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mypage")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public User getUserInfo(@PathVariable Long userId) {
        return userService.getUser(userId);
    }

    @PatchMapping("/{userId}/nickname")
    public User updateNickname(@PathVariable Long userId, @RequestBody String nickname) {
        return userService.updateNickname(userId, nickname);
    }

    @PatchMapping("/{userId}/profile")
    public User updateProfileImage(@PathVariable Long userId, @RequestBody String imageUrl) {
        return userService.updateProfileImage(userId, imageUrl);
    }
}
