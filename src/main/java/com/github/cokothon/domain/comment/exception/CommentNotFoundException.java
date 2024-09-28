package com.github.cokothon.domain.comment.exception;

import com.github.cokothon.common.api.exception.ApiException;
import org.springframework.http.HttpStatus;

public class CommentNotFoundException extends ApiException {

    public CommentNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Comment를 찾을 수 없습니다.");
    }

}
