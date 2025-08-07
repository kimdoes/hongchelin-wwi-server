package com.hongchelin.mypage.service;

import com.hongchelin.mypage.entity.User;
import com.hongchelin.mypage.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow();
    }

    public User updateNickname(Long userId, String nickname) {
        User user = getUser(userId);
        user.updateNickname(nickname);
        return userRepository.save(user);
    }

    public User updateProfileImage(Long userId, String imageUrl) {
        User user = getUser(userId);
        user.updateProfileImageUrl(imageUrl);
        return userRepository.save(user);
    }
}
