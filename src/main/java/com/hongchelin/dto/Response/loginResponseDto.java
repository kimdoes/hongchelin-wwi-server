/**
 *
 */
package com.hongchelin.dto.Response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class loginResponseDto {
    private int code;
    private String message;
    private String redirectUrl;
}