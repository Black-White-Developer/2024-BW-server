package com.github.cokothon.domain.auth.schema;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

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
