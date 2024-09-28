package com.github.cokothon.domain.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record RegisterRequest(

	@NotBlank
	@Email
	String username,

	@NotBlank
	@Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)\\S{8,}$")
	String password
) {
}
