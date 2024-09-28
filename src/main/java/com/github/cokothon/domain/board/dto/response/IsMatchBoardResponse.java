package com.github.cokothon.domain.board.dto.response;

import lombok.Builder;

@Builder
public record IsMatchBoardResponse(
        boolean isMatch
) {
}
