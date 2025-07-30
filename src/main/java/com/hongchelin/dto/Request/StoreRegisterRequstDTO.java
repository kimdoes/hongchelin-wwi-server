package com.hongchelin.dto.Request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class StoreRegisterRequstDTO {
    private String storeName;
    private String storeLocationInMapX;
    private String storeLocationInMapY;
    private String storeImg;
    private String storeLocation;
}
