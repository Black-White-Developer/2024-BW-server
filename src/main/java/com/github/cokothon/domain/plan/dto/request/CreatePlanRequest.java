package com.github.cokothon.domain.plan.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record CreatePlanRequest(

	@NotBlank
	String title,

	@NotBlank
	String content,

	@Min(1)
	int level
) {
}
