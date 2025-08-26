package com.hongchelin.dto.Response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class StoreResponseEntityDTO {
    private Integer status;
    private String message;
    private Iterable<StoreResponseDTO> stores;
}
