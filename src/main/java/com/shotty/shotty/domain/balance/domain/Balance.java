package com.shotty.shotty.domain.balance.domain;

import com.shotty.shotty.domain.balance.exception.custom_exception.NotEnoughBalanceException;
import com.shotty.shotty.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class Balance {
    @Id
    @GeneratedValue
    private Long id;

    private Integer balance;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public static Balance CreateBalance(User user) {
        Balance balance = new Balance();
        balance.balance = 0;
        balance.user = user;
        return balance;
    }

    public void deposit(int amount) {
        balance += amount;
    }

    public void withdraw(int amount) {
        if (amount > balance) {
            throw new NotEnoughBalanceException("잔액보다 더 큰 금액을 출금할 수 없습니다.");
        }

        balance -= amount;
    }
}
