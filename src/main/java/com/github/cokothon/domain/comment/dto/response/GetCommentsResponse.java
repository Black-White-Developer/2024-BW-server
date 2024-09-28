package com.github.cokothon.domain.comment.dto.response;

import java.util.List;

import com.github.cokothon.domain.comment.schema.Comment;

import lombok.Builder;

@Builder
public record GetCommentsResponse(

	List<Comment> comments
) {
}
