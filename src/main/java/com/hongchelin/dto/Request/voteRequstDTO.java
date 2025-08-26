package com.hongchelin.dto.Request;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class voteRequstDTO {
    String storeName;
    String storeIdentifier;
    Integer selectedAmount;

    List<Long> votedIdList;
}
