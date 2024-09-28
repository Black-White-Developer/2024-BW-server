package com.github.cokothon.domain.board.dto.response;

import com.github.cokothon.domain.board.schema.Board;
import lombok.Builder;

import java.util.List;

@Builder
public record GetBoardsResponse(

        List<Board> boards
) {
}
