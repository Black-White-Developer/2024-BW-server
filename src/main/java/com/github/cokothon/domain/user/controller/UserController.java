package com.github.cokothon.domain.user.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.cokothon.common.api.dto.response.ApiResponse;
import com.github.cokothon.common.security.util.UserContext;
import com.github.cokothon.domain.user.dto.request.ChangeInfoRequest;
import com.github.cokothon.domain.user.dto.request.ChangePasswordRequest;
import com.github.cokothon.domain.user.schema.User;
import com.github.cokothon.domain.user.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@PutMapping("/my/info")
	@PreAuthorize("isAuthenticated()")
	public ApiResponse<Void> changeInfo(@RequestBody @Valid ChangeInfoRequest request) {

		User user = UserContext.getUser();

		userService.changeInfo(user, request);

		return ApiResponse.ok();
	}

	@PutMapping("/my/password")
	@PreAuthorize("isAuthenticated()")
	public ApiResponse<Void> changePassword(@RequestBody @Valid ChangePasswordRequest request) {

		User user = UserContext.getUser();

		userService.changePassword(user, request);

		return ApiResponse.ok();
	}

	@PutMapping("/my/avatar")
	@PreAuthorize("isAuthenticated()")
	public ApiResponse<Void> changeAvatar(@RequestPart("avatar") MultipartFile avatar) {

		User user = UserContext.getUser();

		userService.changeAvatar(user, avatar);

		return ApiResponse.ok();
	}

	@DeleteMapping("/my/avatar")
	@PreAuthorize("isAuthenticated()")
	public ApiResponse<Void> deleteAvatar() {

		User user = UserContext.getUser();

		userService.deleteAvatar(user);

		return ApiResponse.ok();
	}
}
