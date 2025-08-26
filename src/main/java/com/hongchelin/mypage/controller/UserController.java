// controller/UserController.java
package com.hongchelin.mypage.controller;

<<<<<<< HEAD
import com.hongchelin.mypage.dto.UserDtos.*;
=======
import com.hongchelin.mypage.dto.UserDtos.AuthorProfile;
import com.hongchelin.mypage.dto.UserDtos.MeResp;
import com.hongchelin.mypage.dto.UserDtos.NicknameReq;
>>>>>>> feature/community
import com.hongchelin.mypage.service.FileStorageService;
import com.hongchelin.mypage.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping({"/api/users/me", "/api/user/me"}) // 임시 호환
public class UserController {
    private final UserService users; private final FileStorageService fs;
    public UserController(UserService users, FileStorageService fs){ this.users = users; this.fs = fs; }

    private Long uid(Long header){ return header==null ? 1L : header; }

    /** 내 프로필 조회 */
    @GetMapping public MeResp me(@RequestHeader(value="X-USER-ID", required=false) Long userId){
        return MeResp.of(users.getOrCreate(uid(userId)));
    }

    /** 닉네임 변경 */
    @PutMapping("/nickname")
    public MeResp changeNickname(@RequestHeader(value="X-USER-ID", required=false) Long userId,
                                 @RequestBody @Valid NicknameReq body){
        return MeResp.of(users.changeNickname(uid(userId), body.nickname()));
    }

    /** 프로필 사진 업로드 변경 */
    @PostMapping(value="/profile-image", consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
    public MeResp changeProfileImage(@RequestHeader(value="X-USER-ID", required=false) Long userId,
                                     @RequestPart("file") MultipartFile file){
        Long id = uid(userId);
        String url = fs.save(file, "profiles/"+id);
        return MeResp.of(users.changeProfileImage(id, url));
    }

    /** 커뮤니티가 사용할 작성자 스냅샷 */
    @GetMapping("/author-profile")
    public AuthorProfile authorProfile(@RequestHeader(value="X-USER-ID", required=false) Long userId){
        return users.authorProfile(uid(userId));
    }

    @PutMapping("/badges/active/{badgeId}")
    public MeResp setActiveBadge(
            @RequestHeader(value="X-USER-ID", required=false) Long userId,
            @PathVariable Long badgeId) {
        Long id = uid(userId);              // null이면 1L 사용
        users.setActiveBadge(id, badgeId);  // 보유 안 한 배지는 403 던짐
        return MeResp.of(users.getOrCreate(id));
    }

}



