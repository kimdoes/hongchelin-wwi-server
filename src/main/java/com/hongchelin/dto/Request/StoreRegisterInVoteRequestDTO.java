package com.hongchelin.dto.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class StoreRegisterInVoteRequestDTO {
    @NotNull(message = "상호명을 입력해주세요")
    @NotBlank(message = "상호명을 입력해주세요")
    private String storeName;

    @NotNull(message = "상호명을 입력해주세요")
    @NotBlank(message = "상호명을 입력해주세요")
    private String storeLocation;
}
