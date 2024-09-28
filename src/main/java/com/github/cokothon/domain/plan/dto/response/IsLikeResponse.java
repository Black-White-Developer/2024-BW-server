package com.github.cokothon.domain.plan.dto.response;

import lombok.Builder;

@Builder
public record IsLikeResponse(

	boolean isLike
) {
}
