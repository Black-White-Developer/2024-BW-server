package com.github.cokothon.domain.auth.service;

import java.util.stream.IntStream;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.github.cokothon.common.email.EmailSender;
import com.github.cokothon.common.security.authentication.UserAuthentication;
import com.github.cokothon.common.security.jwt.JwtUtil;
import com.github.cokothon.domain.auth.dto.request.CheckVerifyCodeRequest;
import com.github.cokothon.domain.auth.dto.request.LoginRequest;
import com.github.cokothon.domain.auth.dto.request.RegisterRequest;
import com.github.cokothon.domain.auth.dto.request.SendVerifyCodeRequest;
import com.github.cokothon.domain.auth.dto.response.CheckVerifyCodeResponse;
import com.github.cokothon.domain.auth.dto.response.LoginResponse;
import com.github.cokothon.domain.auth.dto.response.MyInfoResponse;
import com.github.cokothon.domain.auth.email.VerifyCodeTemplate;
import com.github.cokothon.domain.auth.exception.AlreadyRegisteredEmailException;
import com.github.cokothon.domain.auth.exception.AlreadyRegisteredNicknameException;
import com.github.cokothon.domain.auth.exception.AuthenticationFailException;
import com.github.cokothon.domain.auth.exception.InvalidVerifyCodeException;
import com.github.cokothon.domain.auth.repository.VerifyCodeRepository;
import com.github.cokothon.domain.auth.schema.VerifyCode;
import com.github.cokothon.domain.user.repository.UserRepository;
import com.github.cokothon.domain.user.schema.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final UserRepository userRepository;
	private final VerifyCodeRepository verifyCodeRepository;

	private final AuthenticationManager authenticationManager;
	private final PasswordEncoder encoder;
	private final JwtUtil jwtUtil;

	private final EmailSender emailSender;

	public LoginResponse login(LoginRequest dto) {

		String email = dto.email();
		String password = dto.password();

		User user = userRepository.findByEmail(email)
								  .orElseThrow(AuthenticationFailException::new);

		UserAuthentication authentication = new UserAuthentication(user, password);
		Authentication authenticate = authenticationManager.authenticate(authentication);

		assert authenticate.isAuthenticated();

		String token = jwtUtil.generateToken(user);

		return LoginResponse.builder()
							.token(token)
							.build();
	}

	public void register(RegisterRequest dto) {

		String email = dto.email();
		String password = dto.password();
		String nickname = dto.nickname();
		int level = dto.level();
		String verifyCodeRaw = dto.verifyCode();

		if (userRepository.existsByEmail(email)) {

			throw new AlreadyRegisteredEmailException();
		}

		if (userRepository.existsByNickname(nickname)) {

			throw new AlreadyRegisteredNicknameException();
		}

		VerifyCode verifyCode = verifyCodeRepository.findByEmail(email)
													.orElseThrow(InvalidVerifyCodeException::new);

		if (!verifyCode.code().equals(verifyCodeRaw)) {

			throw new InvalidVerifyCodeException();
		}

		verifyCodeRepository.delete(verifyCode);

		User user = User.builder()
						.email(email)
						.password(encoder.encode(password))
						.nickname(nickname)
						.level(level)
						.build();

		userRepository.save(user);
	}

	public void sendVerifyCode(SendVerifyCodeRequest dto) {

		String email = dto.email();

		if (userRepository.existsByEmail(email)) {

			throw new AlreadyRegisteredEmailException();
		}

		VerifyCode verifyCode = verifyCodeRepository.findByEmail(email)
													.orElseGet(() -> {
														String verifyCodeRaw = IntStream.range(0, 6)
																						.mapToObj(i -> String.valueOf("ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".charAt((int) (Math.random() * 36))))
																						.collect(java.util.stream.Collectors.joining());

														VerifyCode internalVerifyCode = VerifyCode.builder()
																								  .email(email)
																								  .code(verifyCodeRaw)
																								  .build();

														verifyCodeRepository.save(internalVerifyCode);

														return internalVerifyCode;
													});

		emailSender.send(email, VerifyCodeTemplate.of(email, verifyCode.code()));
	}

	public CheckVerifyCodeResponse checkVerifyCode(CheckVerifyCodeRequest dto) {

		String email = dto.email();
		String verifyCodeRaw = dto.verifyCode();

		boolean isVerified = verifyCodeRepository.findByEmail(email)
												 .map(verifyCode -> verifyCode.code().equals(verifyCodeRaw))
												 .orElse(false);

		return CheckVerifyCodeResponse.builder()
									  .isVerified(isVerified)
									  .build();
	}

	public MyInfoResponse me(User user) {

		return MyInfoResponse.builder()
							 .user(user)
							 .build();
	}
}
