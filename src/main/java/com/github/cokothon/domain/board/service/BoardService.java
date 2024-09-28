package com.github.cokothon.domain.board.service;

import com.github.cokothon.domain.auth.exception.NotPermitException;
import com.github.cokothon.domain.board.dto.request.CreateBoardRequest;
import com.github.cokothon.domain.board.dto.response.ReadBoardResponse;
import com.github.cokothon.domain.board.exception.BoardNotFoundException;
import com.github.cokothon.domain.board.repository.BoardRepository;
import com.github.cokothon.domain.board.schema.Board;
import com.github.cokothon.domain.user.schema.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    @PreAuthorize("isAuthenticated()")
    public void createBoard(User user, CreateBoardRequest dto) {

        String title = dto.title();
        String content = dto.content();

        Board board = Board.builder()
                .author(user)
                .title(title)
                .content(content)
                .build();

        boardRepository.save(board);
    }

    public ReadBoardResponse readBoard(String boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(BoardNotFoundException::new);

        ReadBoardResponse readBoardResponse = ReadBoardResponse.builder()
                .board(board)
                .build();

        return readBoardResponse;
    }
}
