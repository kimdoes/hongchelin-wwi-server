package com.hongchelin.dto.Request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberRequestDTO {

    String nickname;

    @NotNull(message = "아이디를 입력해주세요.")
    String userId;

    @NotNull(message = "비밀번호를 입력해주세요")
    @Size(min = 5, message = "비밀번호는 5글자 이상이어야합니다.")
    @Pattern(regexp = ".*[!@#$%^&*(),.?]", message = "특수문자가 반드시 포함되어야 합니다.")
    String password;

    @Email
    String email;
}
