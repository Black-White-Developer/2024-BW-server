package com.github.cokothon.domain.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record SendVerifyCodeRequest(

        @NotBlank
        @Email
        String email
) {
}
