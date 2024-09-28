package com.github.cokothon.domain.auth.exception;

import org.springframework.http.HttpStatus;

import com.github.cokothon.common.api.exception.ApiException;

public class AuthenticationFailException extends ApiException {

	public AuthenticationFailException() {

		super(HttpStatus.UNAUTHORIZED, "인증에 실패하였습니다.");
	}
}
