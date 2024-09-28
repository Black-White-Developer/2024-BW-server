package com.github.cokothon.domain.auth.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.cokothon.common.api.dto.response.ApiResponse;
import com.github.cokothon.common.security.util.UserContext;
import com.github.cokothon.domain.auth.dto.request.CheckVerifyCodeRequest;
import com.github.cokothon.domain.auth.dto.request.LoginRequest;
import com.github.cokothon.domain.auth.dto.request.RegisterRequest;
import com.github.cokothon.domain.auth.dto.request.SendVerifyCodeRequest;
import com.github.cokothon.domain.auth.dto.response.CheckVerifyCodeResponse;
import com.github.cokothon.domain.auth.dto.response.LoginResponse;
import com.github.cokothon.domain.auth.dto.response.MyInfoResponse;
import com.github.cokothon.domain.auth.service.AuthService;
import com.github.cokothon.domain.user.schema.User;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping("/login")
	public ApiResponse<LoginResponse> login(@RequestBody @Valid LoginRequest request) {

		return ApiResponse.ok(authService.login(request));
	}

	@PostMapping("/register")
	public ApiResponse<Void> register(@RequestBody @Valid RegisterRequest request) {

		authService.register(request);

		return ApiResponse.ok();
	}

	@PostMapping("/verify-code/send")
	public ApiResponse<Void> sendVerifyCode(@RequestBody @Valid SendVerifyCodeRequest request) {

		authService.sendVerifyCode(request);

		return ApiResponse.ok();
	}

	@PostMapping("/verify-code/check")
	public ApiResponse<CheckVerifyCodeResponse> register(@RequestBody @Valid CheckVerifyCodeRequest request) {

		return ApiResponse.ok(authService.checkVerifyCode(request));
	}

	@GetMapping("/me")
	@PreAuthorize("isAuthenticated()")
	public ApiResponse<MyInfoResponse> me() {

		User user = UserContext.getUser();

		return ApiResponse.ok(authService.me(user));
	}
}
