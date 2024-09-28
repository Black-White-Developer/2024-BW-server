package com.github.cokothon.domain.auth.dto.response;

import com.github.cokothon.domain.user.schema.User;

import lombok.Builder;

@Builder
public record MyInfoResponse(

	User user
) {
}
