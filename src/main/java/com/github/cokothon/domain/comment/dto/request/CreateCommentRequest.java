package com.github.cokothon.domain.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record CreateCommentRequest(

        @NotBlank
        String content
) {
}
