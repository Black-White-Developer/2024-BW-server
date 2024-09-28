package com.github.cokothon.common.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "app.jwt")
public class JwtProperty {

	@NotBlank
	private String key;

	@NotNull
	private Integer expirationHours;
}
