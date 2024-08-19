package com.shotty.shotty.domain.account.domain;

import com.shotty.shotty.domain.account.dto.AccountCreateReqDto;
import com.shotty.shotty.domain.account.dto.AccountUpdateReqDto;
import com.shotty.shotty.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "accounts")
@Getter
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue
    private Long id;

    private String account;

    private String bank;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Account(String account, String bank, User user) {
        this.account = account;
        this.bank = bank;
        this.user = user;
    }

    public void updateAccount(AccountUpdateReqDto accountUpdateReqDto) {
        this.account = accountUpdateReqDto.account();
        this.bank = accountUpdateReqDto.bank();
    }

    public static Account of(AccountCreateReqDto accountCreateReqDto, User user) {
        return new Account(
                accountCreateReqDto.account(),
                accountCreateReqDto.bank(),
                user
        );
    }
}
