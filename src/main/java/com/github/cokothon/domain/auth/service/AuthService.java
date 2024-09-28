package com.github.cokothon.domain.auth.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.github.cokothon.common.security.authentication.UserAuthentication;
import com.github.cokothon.common.security.jwt.JwtUtil;
import com.github.cokothon.domain.auth.dto.request.LoginRequest;
import com.github.cokothon.domain.auth.dto.request.RegisterRequest;
import com.github.cokothon.domain.auth.dto.response.LoginResponse;
import com.github.cokothon.domain.auth.dto.response.MyInfoResponse;
import com.github.cokothon.domain.auth.exception.AlreadyRegisteredEmailException;
import com.github.cokothon.domain.auth.exception.AuthenticationFailException;
import com.github.cokothon.domain.user.repository.UserRepository;
import com.github.cokothon.domain.user.schema.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final UserRepository userRepository;

	private final AuthenticationManager authenticationManager;
	private final PasswordEncoder encoder;
	private final JwtUtil jwtUtil;

	public LoginResponse login(LoginRequest dto) {

		String username = dto.username();
		String password = dto.password();

		User user = userRepository.findByUsername(username)
			.orElseThrow(AuthenticationFailException::new);

		UserAuthentication authentication = new UserAuthentication(user, password);
		Authentication authenticate = authenticationManager.authenticate(authentication);

		assert authenticate.isAuthenticated();

		String token = jwtUtil.generateAccessToken(user);

		return LoginResponse.builder()
			.token(token)
			.build();
	}

	public void register(RegisterRequest dto) {

		String username = dto.username();
		String password = dto.password();

		if (userRepository.existsByUsername(username)) {

			throw new AlreadyRegisteredEmailException();
		}

		User user = User.builder()
			.username(username)
			.password(encoder.encode(password))
			.role(User.Role.MEMBER)
			.build();

		userRepository.save(user);
	}

	public MyInfoResponse me(User user) {

		return MyInfoResponse.builder()
			.user(user)
			.build();
	}
}
