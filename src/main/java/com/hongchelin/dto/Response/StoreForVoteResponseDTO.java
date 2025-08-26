package com.hongchelin.dto.Response;

import com.hongchelin.Domain.StoreForVote;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
@Builder
public class StoreForVoteResponseDTO {
    private Integer status;
    private String message;
    private Iterable<StoreResponseDTO> storeForVote;
    private Boolean voteAvailable;
    private String redirectUrl;
}
