package com.github.cokothon.domain.board.controller;

import com.github.cokothon.common.api.dto.response.ApiResponse;
import com.github.cokothon.common.security.util.UserContext;
import com.github.cokothon.domain.board.dto.request.CreateBoardRequest;
import com.github.cokothon.domain.board.dto.response.GetBoardResponse;
import com.github.cokothon.domain.board.dto.response.GetBoardsResponse;
import com.github.cokothon.domain.board.service.BoardService;
import com.github.cokothon.domain.user.schema.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor

public class BoardController {

    private final BoardService boardService;

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public ApiResponse<Void> createBoard(@RequestBody @Valid CreateBoardRequest request) {

        User user = UserContext.getUser();

        boardService.createBoard(user, request);
        return ApiResponse.ok();
    }

    @GetMapping("/{boardId}")
    public ApiResponse<GetBoardResponse> readBoard(@PathVariable("boardId") String boardId) {

        return ApiResponse.ok(boardService.readBoard(boardId));
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{boardId}")
    public ApiResponse<Void> modifyBoard(@PathVariable("boardId") String boardId, @RequestBody CreateBoardRequest dto) {
        User user = UserContext.getUser();

        boardService.modifyBoard(boardId, user, dto);

        return ApiResponse.ok();
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{boardId}")
    public ApiResponse<Void> deleteBoard(@PathVariable("boardId") String boardId) {
        User user = UserContext.getUser();

        boardService.deleteBoard(boardId, user);

        return ApiResponse.ok();
    }

    @GetMapping()
    public ApiResponse<GetBoardsResponse> getBoards() {
        return ApiResponse.ok(boardService.getBoards());
    }

    @GetMapping("/best")
    public ApiResponse<GetBoardsResponse> getBestBoards(){
        return ApiResponse.ok(boardService.getBestBoards());
    }
}
