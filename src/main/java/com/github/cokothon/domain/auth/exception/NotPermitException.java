package com.github.cokothon.domain.auth.exception;

import org.springframework.http.HttpStatus;

import com.github.cokothon.common.api.exception.ApiException;

public class NotPermitException extends ApiException {

	public NotPermitException() {

		super(HttpStatus.UNAUTHORIZED, "권한이 없습니다.");
	}
}

