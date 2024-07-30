package com.shotty.shotty.domain.auth.application;

import com.shotty.shotty.domain.auth.domain.User;
import com.shotty.shotty.domain.auth.dto.EncryptedUserDto;
import com.shotty.shotty.domain.auth.dto.UserResponseDto;
import com.shotty.shotty.domain.auth.exception.custom_exception.user.UserIdDuplicateException;
import com.shotty.shotty.domain.auth.dao.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserResponseDto register(EncryptedUserDto encryptedUserDto) {
        User user;
        try {
            user = userRepository.save(User.from(encryptedUserDto));
        } catch (DataIntegrityViolationException e) {
            throw new UserIdDuplicateException("이미 존재하는 사용자입니다.");
        }

        return UserResponseDto.from(user);
    }
}
