package com.github.cokothon.domain.comment.controller;

import com.github.cokothon.common.api.dto.response.ApiResponse;
import com.github.cokothon.common.security.util.UserContext;
import com.github.cokothon.domain.comment.dto.request.CreateCommentRequest;
import com.github.cokothon.domain.comment.dto.response.GetCommentsResponse;
import com.github.cokothon.domain.comment.service.CommentService;
import com.github.cokothon.domain.user.schema.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor

public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{parent}/{parentId}")
    @PreAuthorize("isAuthenticated()")
    public ApiResponse<Void> createComment(@PathVariable("parent") String parent, @PathVariable String parentId , @RequestBody
    CreateCommentRequest dto) {

        User user = UserContext.getUser();

        commentService.createComment(parent, parentId, dto, user);

        return ApiResponse.ok();
    }

    @PutMapping("/{commentId}")
    @PreAuthorize("isAuthenticated()")
    public ApiResponse<Void> modifyComment(@PathVariable("commentId") String commentId, @RequestBody CreateCommentRequest dto) {

        User user = UserContext.getUser();

        commentService.modifyComment(user, commentId, dto);

        return ApiResponse.ok();
    }

    @DeleteMapping("/{commentId}")
    @PreAuthorize("isAuthenticated()")
    public ApiResponse<Void> deleteComment(@PathVariable("commentId") String commentId) {

        User user = UserContext.getUser();

        commentService.deleteComment(user, commentId);

        return ApiResponse.ok();
    }

    @GetMapping("/{parent}/{parentId}")
    public ApiResponse<GetCommentsResponse> getComments(@PathVariable("parent") String parent, @PathVariable("parentId") String parentId) {

        return ApiResponse.ok(commentService.getComments(parent, parentId));
    }
}
