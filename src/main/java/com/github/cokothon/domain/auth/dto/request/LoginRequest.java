package com.github.cokothon.domain.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record LoginRequest(

	@NotBlank
	String username,

	@NotBlank
	String password
) {
}
