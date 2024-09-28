package com.github.cokothon.domain.board.exception;

import org.springframework.http.HttpStatus;

import com.github.cokothon.common.api.exception.ApiException;

public class BoardNotFoundException extends ApiException {

  public BoardNotFoundException() {

    super(HttpStatus.NOT_FOUND, "Board를 찾을 수 없습니다.");
  }
}
