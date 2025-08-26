package com.hongchelin.dto.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class StoreRegisterRequstDTO {

    @NotBlank(message = "상호명을 입력해주세요")
    @NotNull(message = "상호명을 입력해주세요")
    private String storeName;

    private String storeInfoOneline;
    private String storeImg;

    @NotBlank(message = "가게주소를 입력해주세요")
    @NotNull(message = "가게주소를 입력해주세요")
    private String storeLocation;
}
