package com.github.cokothon.domain.auth.dto.response;

import lombok.Builder;

@Builder
public record CheckVerifyCodeResponse(

        boolean isVerified
) {
}
