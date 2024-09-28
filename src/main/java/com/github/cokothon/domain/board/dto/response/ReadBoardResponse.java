package com.github.cokothon.domain.board.dto.response;

import com.github.cokothon.domain.board.schema.Board;
import lombok.Builder;

@Builder
public record ReadBoardResponse(

        Board board
) {
}
