package com.github.cokothon.domain.user.exception;

import org.springframework.http.HttpStatus;

import com.github.cokothon.common.api.exception.ApiException;

public class AvatarNotImageException extends ApiException {

    public AvatarNotImageException() {

        super(HttpStatus.BAD_REQUEST, "프로필 사진이 이미지 파일이 아닙니다.");
    }
}
