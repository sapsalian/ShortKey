package com.shotty.shotty.domain.account.application;

import com.shotty.shotty.domain.account.dao.AccountRepository;
import com.shotty.shotty.domain.account.domain.Account;
import com.shotty.shotty.domain.account.dto.AccountCreateReqDto;
import com.shotty.shotty.domain.account.dto.AccountResDto;
import com.shotty.shotty.domain.account.dto.AccountUpdateReqDto;
import com.shotty.shotty.domain.account.exception.custom_exception.NoBankAccountException;
import com.shotty.shotty.domain.user.dao.UserRepository;
import com.shotty.shotty.domain.user.domain.User;
import com.shotty.shotty.global.common.exception.custom_exception.NoSuchResourcException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public AccountResDto createAccount(AccountCreateReqDto accountCreateReqDto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchResourcException("존재하지 않는 유저의 요청입니다."));

        Account account = Account.of(accountCreateReqDto, user);
        account = accountRepository.save(account);

        return AccountResDto.from(account);
    }

    public AccountResDto getAccountOf(Long userId) {
        Account account = accountRepository.findByUserId(userId)
                .orElseThrow(NoBankAccountException::new);

        return AccountResDto.from(account);
    }

    public AccountResDto updateAccount(AccountUpdateReqDto accountUpdateReqDto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchResourcException("존재하지 않는 유저의 요청입니다."));

        Account account = accountRepository.findByUserId(userId)
                .orElseThrow(NoBankAccountException::new);

        account.updateAccount(accountUpdateReqDto);
        account = accountRepository.save(account);

        return AccountResDto.from(account);
    }
}
