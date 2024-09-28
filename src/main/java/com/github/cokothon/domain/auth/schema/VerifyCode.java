package com.github.cokothon.domain.auth.schema;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import lombok.Builder;

@Builder
@RedisHash(value = "verify_code", timeToLive = 60 * 60)
public record VerifyCode(

	@Id
	Long id,

	@Indexed
	String email,

	String code
) {
}
