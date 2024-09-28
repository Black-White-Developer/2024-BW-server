package com.github.cokothon.domain.auth.exception;

import org.springframework.http.HttpStatus;

import com.github.cokothon.common.api.exception.ApiException;

public class InvalidVerifyCodeException extends ApiException {

	public InvalidVerifyCodeException() {

		super(HttpStatus.BAD_REQUEST, "유효하지 않은 인증 코드입니다.");
	}
}
