package com.shotty.shotty.service;

import com.shotty.shotty.Domain.User;
import com.shotty.shotty.dto.EncryptedUserDto;
import com.shotty.shotty.dto.ResisterRequestDto;
import com.shotty.shotty.dto.UserResponseDto;
import com.shotty.shotty.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserResponseDto register(EncryptedUserDto encryptedUserDto) {
        User user = userRepository.save(User.from(encryptedUserDto));

        return UserResponseDto.from(user);
    }
}
