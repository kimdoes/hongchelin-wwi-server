// service/UserService.java
package com.hongchelin.mypage.service;

import com.hongchelin.mypage.entity.User;
import com.hongchelin.mypage.exception.ApiException;
import com.hongchelin.mypage.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @Transactional
public class UserService {
    private final UserRepository users;
    public UserService(UserRepository users){ this.users = users; }

    /** 개발 편의: 없으면 자동 생성 (닉네임 user{id}) */
    public User getOrCreate(Long id){
        return users.findById(id).orElseGet(() -> users.save(User.createWithId(id, "user"+id)));
    }

    public User changeNickname(Long userId, String nickname){
        if (nickname.length() > 30) throw new ApiException(HttpStatus.BAD_REQUEST, "닉네임은 30자 이하");
        User u = getOrCreate(userId); u.changeNickname(nickname); return u;
    }

    public User changeProfileImage(Long userId, String url){
        User u = getOrCreate(userId); u.changeProfileImage(url); return u;
    }

    public void setActiveBadge(Long userId, Long badgeId){
        getOrCreate(userId).setActiveBadge(badgeId);
    }
}
