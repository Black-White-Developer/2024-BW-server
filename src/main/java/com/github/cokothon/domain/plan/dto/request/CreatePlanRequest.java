package com.github.cokothon.domain.plan.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record CreatePlanRequest(

        @NotBlank
        String title,

        @NotBlank
        String content
) {
}
