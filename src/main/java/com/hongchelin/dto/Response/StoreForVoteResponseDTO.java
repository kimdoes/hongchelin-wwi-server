package com.hongchelin.dto.Response;

import com.hongchelin.Domain.StoreForVote;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StoreForVoteResponseDTO {
    private Integer status;
    private String message;
    private Iterable<StoreForVote> storeForVote;
    private Boolean voteAvailable;
    private String redirectUrl;
}
