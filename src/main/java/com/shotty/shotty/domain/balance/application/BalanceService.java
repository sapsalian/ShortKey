package com.shotty.shotty.domain.balance.application;

import com.shotty.shotty.domain.balance.dao.BalanceRepository;
import com.shotty.shotty.domain.balance.domain.Balance;
import com.shotty.shotty.domain.balance.dto.BalanceResDto;
import com.shotty.shotty.domain.balance.dto.ChangeBalanceDto;
import com.shotty.shotty.domain.balance.exception.custom_exception.BalanceNotFoundException;
import com.shotty.shotty.domain.user.dao.UserRepository;
import com.shotty.shotty.domain.user.domain.User;
import com.shotty.shotty.domain.user.exception.custom_exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BalanceService {
    private final UserRepository userRepository;
    private final BalanceRepository balanceRepository;

    public BalanceResDto getBalanceByUserId(Long userId) {
        Balance balance = balanceRepository.findByUserId(userId)
                .orElseThrow(() -> new BalanceNotFoundException("해당 유저의 잔여금 정보가 존재하지 않습니다"));

        return BalanceResDto.from(balance);
    }

    public BalanceResDto deposit(Long userId, ChangeBalanceDto changeBalanceDto) {
        Balance balance = balanceRepository.findByUserId(userId)
                .orElseThrow(() -> new BalanceNotFoundException("해당 유저의 잔여금 정보가 존재하지 않습니다"));

        balance.deposit(changeBalanceDto.amount());
        balanceRepository.save(balance);

        return BalanceResDto.from(balance);
    }

    public  BalanceResDto withdraw(Long userId, ChangeBalanceDto changeBalanceDto) {
        Balance balance = balanceRepository.findByUserId(userId)
                .orElseThrow(() -> new BalanceNotFoundException("해당 유저의 잔여금 정보가 존재하지 않습니다"));


        balance.withdraw(changeBalanceDto.amount());
        balanceRepository.save(balance);

        return BalanceResDto.from(balance);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void transfer(Long fromUserId, Long toUserId, Integer amount) {
        Balance from = balanceRepository.findByUserId(fromUserId)
                .orElseThrow(() -> new BalanceNotFoundException("해당 유저의 잔여금 정보가 존재하지 않습니다"));

        Balance to = balanceRepository.findByUserId(toUserId)
                .orElseThrow(() -> new BalanceNotFoundException("해당 유저의 잔여금 정보가 존재하지 않습니다"));

        from.withdraw(amount);
        to.deposit(amount);

        balanceRepository.save(from);
        balanceRepository.save(to);
    }
}
