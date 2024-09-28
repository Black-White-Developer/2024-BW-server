package com.github.cokothon.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ChangeInfoRequest(

        @NotBlank
        String nickname
) {
}
