package com.github.cokothon.domain.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record RegisterRequest(

	@NotBlank
	@Email
	String email,

	@NotBlank
	@Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)\\S{8,}$")
	String password,

	@NotBlank
	String nickname,

	@NotNull
	@Min(0)
	@Max(100)
	Integer level,

	@NotBlank
	String verifyCode
) {
}
