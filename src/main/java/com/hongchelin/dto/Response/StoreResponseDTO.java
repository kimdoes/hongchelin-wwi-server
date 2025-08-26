package com.hongchelin.dto.Response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StoreResponseDTO {
    private long storeId;
    private String storeName;
    private String storeImg;
    private String storeLocation;
    private String storeInfoOneline;
    private Integer votedCount;

    private Boolean isSelected;
    private String whenForVote;
}