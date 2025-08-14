package com.hongchelin.dto.Response;

import com.hongchelin.Domain.PastStoreForVote;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PastStoreForVoteResponseDTO {
    private Integer status;
    private String message;
    private String whenForVote;
    private List<PastStoreForVote> storeForPastVote;
}
