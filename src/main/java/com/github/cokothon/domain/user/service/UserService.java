package com.github.cokothon.domain.user.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.github.cokothon.common.s3.S3Service;
import com.github.cokothon.common.security.authentication.UserAuthentication;
import com.github.cokothon.domain.user.dto.request.ChangeInfoRequest;
import com.github.cokothon.domain.user.dto.request.ChangePasswordRequest;
import com.github.cokothon.domain.user.exception.AvatarNotImageException;
import com.github.cokothon.domain.user.repository.UserRepository;
import com.github.cokothon.domain.user.schema.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final S3Service s3Service;

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public void changeInfo(User user, ChangeInfoRequest dto) {

        String nickname = dto.nickname();

        user.setNickname(nickname);

        userRepository.save(user);
    }

    public void changePassword(User user, ChangePasswordRequest dto) {

        String password = dto.password();
        String newPassword = dto.newPassword();

        UserAuthentication authentication = new UserAuthentication(user, password);
        Authentication authenticate = authenticationManager.authenticate(authentication);

        assert authenticate.isAuthenticated();

        user.setPassword(passwordEncoder.encode(newPassword));

        userRepository.save(user);
    }

    public void changeAvatar(User user, MultipartFile avatar) {

        if (avatar.getOriginalFilename() == null || avatar.getContentType() != null && !avatar.getContentType().startsWith("image")) {

            throw new AvatarNotImageException();
        }

        String extension = avatar.getOriginalFilename().substring(avatar.getOriginalFilename().lastIndexOf(".") + 1);
        String avatarUrl = s3Service.uploadFile("avatar/%s.%s".formatted(user.getId(), extension), avatar);

        user.setAvatar(avatarUrl);

        userRepository.save(user);
    }

    public void deleteAvatar(User user) {

        if (user.getAvatar() == null) {

            return;
        }

        s3Service.deleteFile(user.getAvatar().split("amazonaws.com/")[1]);

        user.setAvatar(null);

        userRepository.save(user);
    }
}
