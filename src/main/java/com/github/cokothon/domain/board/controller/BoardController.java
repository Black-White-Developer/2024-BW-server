package com.github.cokothon.domain.board.controller;

import com.github.cokothon.common.api.dto.response.ApiResponse;
import com.github.cokothon.common.security.util.UserContext;
import com.github.cokothon.domain.board.dto.request.CreateBoardRequest;
import com.github.cokothon.domain.board.dto.response.ReadBoardResponse;
import com.github.cokothon.domain.board.schema.Board;
import com.github.cokothon.domain.board.service.BoardService;
import com.github.cokothon.domain.user.repository.UserRepository;
import com.github.cokothon.domain.user.schema.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor

public class BoardController {

    private final UserRepository userRepository;
    private final BoardService boardService;

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public ApiResponse<Void> createBoard(@RequestBody @Valid CreateBoardRequest request) {
        User user = UserContext.getUser();

        boardService.createBoard(user, request);
        return ApiResponse.ok();
    }

    @GetMapping("/read/{boardId}")
    public ApiResponse<ReadBoardResponse> readBoard(@PathVariable("boardId") String boardId) {
        return ApiResponse.ok(boardService.readBoard(boardId));
    }
}
