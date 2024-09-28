package com.github.cokothon.domain.board.controller;

import com.github.cokothon.common.api.dto.response.ApiResponse;
import com.github.cokothon.common.security.util.UserContext;
import com.github.cokothon.domain.board.dto.response.IsLikeResponse;
import com.github.cokothon.domain.board.service.BoardLikeService;
import com.github.cokothon.domain.user.schema.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/board/like")
@RequiredArgsConstructor
public class BoardLikeController {

    private final BoardLikeService boardLikeService;

    @GetMapping("/{boardId}")
    @PreAuthorize("isAuthenticated()")
    public ApiResponse<IsLikeResponse> isLikeBoard(@PathVariable("boardId") String boardId) {

        User user = UserContext.getUser();

        return ApiResponse.ok(boardLikeService.isLikeBoard(user, boardId));
    }

    @PutMapping("/{boardId}")
    @PreAuthorize("isAuthenticated()")
    public ApiResponse<Void> likeBoard(@PathVariable("boardId") String boardId) {

        User user = UserContext.getUser();

        boardLikeService.likeBoard(user, boardId);

        return ApiResponse.ok();
    }

    @DeleteMapping("/{boardId}")
    @PreAuthorize("isAuthenticated()")
    public ApiResponse<Void> unlikeBoard(@PathVariable("boardId") String boardId) {

        User user = UserContext.getUser();

        boardLikeService.unlikeBoard(user, boardId);

        return ApiResponse.ok();
    }
}
