package com.github.cokothon.domain.auth.exception;

import org.springframework.http.HttpStatus;

import com.github.cokothon.common.api.exception.ApiException;

public class AlreadyRegisteredEmailException extends ApiException {

	public AlreadyRegisteredEmailException() {

		super(HttpStatus.CONFLICT, "이미 가입된 이메일입니다.");
	}
}
