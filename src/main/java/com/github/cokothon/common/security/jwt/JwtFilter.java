package com.github.cokothon.common.security.jwt;

import java.io.IOException;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.github.cokothon.common.security.authentication.UserAuthentication;
import com.github.cokothon.domain.auth.exception.AuthenticationFailException;
import com.github.cokothon.domain.user.repository.UserRepository;
import com.github.cokothon.domain.user.schema.User;

import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;
	private final UserRepository repository;

	@Override
	public void doFilterInternal(
		@Nonnull HttpServletRequest request,
		@Nonnull HttpServletResponse response,
		@Nonnull FilterChain filterChain
	) throws ServletException, IOException {

		String accessToken = extractToken(request);

		if (accessToken != null && jwtUtil.validateToken(accessToken)) {

			String id = jwtUtil.extractToken(accessToken);
			User user = repository.findById(id)
				.orElseThrow(AuthenticationFailException::new);

			UserAuthentication authentication = new UserAuthentication(user);
			authentication.setAuthenticated(true);

			SecurityContextHolder.getContext().setAuthentication(authentication);
		}

		filterChain.doFilter(request, response);
	}

	private String extractToken(HttpServletRequest request) {
		String authorization = request.getHeader("Authorization");

		return (authorization != null && authorization.startsWith("Bearer "))
			? authorization.substring(7)
			: null;
	}
}
