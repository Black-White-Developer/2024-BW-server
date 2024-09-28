package com.github.cokothon.domain.board.dto.request;

import com.github.cokothon.domain.user.schema.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record CreateBoardRequest (

    @NotBlank
    String title,

    @NotBlank
    String content
)
{
}