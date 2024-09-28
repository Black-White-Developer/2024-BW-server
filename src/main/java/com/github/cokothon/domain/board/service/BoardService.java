package com.github.cokothon.domain.board.service;

import com.github.cokothon.domain.board.dto.request.CreateBoardRequest;
import com.github.cokothon.domain.board.repository.BoardRepository;
import com.github.cokothon.domain.board.schema.Board;
import com.github.cokothon.domain.user.repository.UserRepository;
import com.github.cokothon.domain.user.schema.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

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
}
