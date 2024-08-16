package com.shotty.shotty.domain.balance.domain;

import com.shotty.shotty.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "balances")
@Getter
@NoArgsConstructor
public class Balance {
    @Id
    @GeneratedValue
    private Long id;

    private Long totalAmount;

    private String account;

    private String bank;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void deposit(Long amount) {
        totalAmount += amount;
    }

    public void withdraw(Long amount) {
        totalAmount -= amount;
    }
}
