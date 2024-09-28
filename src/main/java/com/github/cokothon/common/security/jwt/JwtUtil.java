package com.github.cokothon.common.security.jwt;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.github.cokothon.common.property.JwtProperty;
import com.github.cokothon.domain.user.schema.User;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtUtil {

	private final JwtProperty jwtProperty;

	private Algorithm algorithm;

	@PostConstruct
	public void init() {

		algorithm = Algorithm.HMAC256(jwtProperty.getKey());
	}

	public String generateToken(User user) {

		return generateToken(user.getId());
	}

	public String generateToken(String userId) {

		return JWT.create()
			.withIssuedAt(Instant.now())
			.withExpiresAt(Instant.now().plus(jwtProperty.getExpirationHours(), ChronoUnit.SECONDS))
			.withClaim("id", userId)
			.sign(algorithm);
	}

	public String extractToken(String token) {

		return JWT.require(algorithm)
			.build()
			.verify(token)
			.getClaim("id")
			.asString();
	}

	public boolean validateToken(String token) {

		try {
			if (token == null) {

				return false;
			}

			JWT.require(algorithm).build().verify(token);
		} catch (Exception ignored) {
			return false;
		}

		return true;
	}
}
