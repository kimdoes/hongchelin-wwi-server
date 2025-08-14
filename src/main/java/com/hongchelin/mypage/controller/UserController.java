// controller/UserController.java
package com.hongchelin.mypage.controller;

import com.hongchelin.mypage.dto.UserDtos.*;
import com.hongchelin.mypage.service.FileStorageService;
import com.hongchelin.mypage.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/users/me")
public class UserController {
    private final UserService users; private final FileStorageService fs;
    public UserController(UserService users, FileStorageService fs){ this.users = users; this.fs = fs; }

    @GetMapping
    public MeResp me(@RequestHeader(value="X-USER-ID", required=false) Long userId){
        Long uid = (userId == null ? 1L : userId);
        return MeResp.of(users.getOrCreate(uid));
    }

    @PutMapping("/nickname")
    public MeResp changeNickname(@RequestHeader(value="X-USER-ID", required=false) Long userId,
                                 @RequestBody @Valid NicknameReq body){
        Long uid = (userId == null ? 1L : userId);
        return MeResp.of(users.changeNickname(uid, body.nickname()));
    }

    @PostMapping(value="/profile-image", consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
    public MeResp changeProfileImage(@RequestHeader(value="X-USER-ID", required=false) Long userId,
                                     @RequestPart("file") MultipartFile file){
        Long uid = (userId == null ? 1L : userId);
        String url = fs.save(file, "profiles/"+uid);
        return MeResp.of(users.changeProfileImage(uid, url));
    }
}


