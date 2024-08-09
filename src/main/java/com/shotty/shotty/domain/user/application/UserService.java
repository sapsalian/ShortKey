package com.shotty.shotty.domain.user.application;

import com.shotty.shotty.domain.influencer.application.InfluencerService;
import com.shotty.shotty.domain.user.domain.User;
import com.shotty.shotty.domain.user.domain.UserPatch;
import com.shotty.shotty.domain.user.dto.EncryptedUserDto;
import com.shotty.shotty.domain.user.dto.UserPatchRequestDto;
import com.shotty.shotty.domain.user.dto.UserResponseDto;
import com.shotty.shotty.domain.user.exception.custom_exception.UserIdDuplicateException;
import com.shotty.shotty.domain.user.dao.UserRepository;
import com.shotty.shotty.domain.user.exception.custom_exception.UserNotFoundException;
import com.shotty.shotty.global.auth.dao.RefreshTokenRepository;
import com.shotty.shotty.global.util.PatchUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final InfluencerService influencerService;

    public void register(EncryptedUserDto encryptedUserDto) {
        try {
            userRepository.save(User.from(encryptedUserDto));
        } catch (DataIntegrityViolationException e) {
            throw new UserIdDuplicateException("이미 존재하는 사용자입니다.");
        }
    }

    public UserResponseDto findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException("존재하지 않는 사용자입니다.")
        );

        return UserResponseDto.from(user);
    }


    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("존재하지 않는 사용자입니다.");
        }

        influencerService.deleteByUserId(id);
        userRepository.deleteById(id);
    }

    public UserResponseDto patch(Long id, UserPatch userPatch) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException("존재하지 않는 사용자입니다.")
        );

        PatchUtil.applyPatch(user, userPatch);
        user = userRepository.save(user);

        return UserResponseDto.from(user);
    }

    public void logout(Long user_id) {
        refreshTokenRepository.deleteByUserId(user_id);
    }
}
