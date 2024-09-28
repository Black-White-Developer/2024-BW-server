package com.github.cokothon.common.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "spring.data.redis")
public class RedisProperty {

	@NotBlank
	private String host;

	@NotNull
	@Min(1)
	@Max(65535)
	private Integer port;

	@Nullable
	private String password;
}
