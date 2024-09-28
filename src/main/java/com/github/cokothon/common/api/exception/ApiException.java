package com.github.cokothon.common.api.exception;

import org.springframework.http.HttpStatus;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiException extends RuntimeException {

	private final HttpStatus httpStatus;
	private final String message;
}
