package com.shotty.shotty.domain.balance.application;

import com.shotty.shotty.domain.balance.dto.BalanceResDto;
import com.shotty.shotty.domain.user.dao.UserRepository;
import com.shotty.shotty.domain.user.domain.User;
import com.shotty.shotty.domain.user.exception.custom_exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BalanceService {
    private final UserRepository userRepository;

    public BalanceResDto getBalanceByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("사용자가 존재하지 않습니다."));

        return BalanceResDto.from(user);
    }
}
