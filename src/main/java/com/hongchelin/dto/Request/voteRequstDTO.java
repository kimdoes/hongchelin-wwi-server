package com.hongchelin.dto.Request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class voteRequstDTO {
    String storeName;
    String storeIdentifier;
    Integer selectedAmount;
}
