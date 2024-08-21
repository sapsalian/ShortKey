package com.shotty.shotty.domain.balance.application;

import com.shotty.shotty.domain.balance.dto.BalanceResDto;
import com.shotty.shotty.domain.balance.dto.ChangeBalanceDto;
import com.shotty.shotty.domain.user.dao.UserRepository;
import com.shotty.shotty.domain.user.domain.User;
import com.shotty.shotty.domain.user.exception.custom_exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BalanceService {
    private final UserRepository userRepository;

    public BalanceResDto getBalanceByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 사용자입니다."));

        return BalanceResDto.from(user);
    }

    public BalanceResDto deposit(Long userId, ChangeBalanceDto changeBalanceDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 사용자입니다."));


        user.deposit(changeBalanceDto.amount());
        user = userRepository.save(user);

        return BalanceResDto.from(user);
    }

    public  BalanceResDto withdraw(Long userId, ChangeBalanceDto changeBalanceDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 사용자입니다."));


        user.withdraw(changeBalanceDto.amount());
        user = userRepository.save(user);

        return BalanceResDto.from(user);
    }

    @Transactional
    public void Transfer(Long fromUserId, Long toUserId, ChangeBalanceDto changeBalanceDto) {
        User from = userRepository.findById(fromUserId)
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 유저의 전송 요청입니다."));

        User to = userRepository.findById(toUserId)
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 대상으로의 전송입니다."));

        from.withdraw(changeBalanceDto.amount());
        to.deposit(changeBalanceDto.amount());

        userRepository.save(from);
        userRepository.save(to);
    }
}
