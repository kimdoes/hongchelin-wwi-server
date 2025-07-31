package com.hongchelin.dto.Request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class StoreRegisterInVoteRequestDTO {
    @NotNull
    private String storeName;

    @NotNull
    private String storeLocation;
}
