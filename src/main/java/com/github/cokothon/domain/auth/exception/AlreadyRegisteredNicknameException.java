package com.github.cokothon.domain.auth.exception;

import org.springframework.http.HttpStatus;

import com.github.cokothon.common.api.exception.ApiException;

public class AlreadyRegisteredNicknameException extends ApiException {

	public AlreadyRegisteredNicknameException() {

		super(HttpStatus.CONFLICT, "이미 가입된 닉네임입니다.");
	}
}
