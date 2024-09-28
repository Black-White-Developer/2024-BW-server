package com.github.cokothon.domain.plan.exception;

import org.springframework.http.HttpStatus;

import com.github.cokothon.common.api.exception.ApiException;

public class PlanNotFoundException extends ApiException {

	public PlanNotFoundException() {

		super(HttpStatus.NOT_FOUND, "Plan을 찾을 수 없습니다.");
	}
}
