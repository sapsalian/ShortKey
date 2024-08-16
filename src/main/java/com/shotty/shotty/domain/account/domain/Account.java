package com.shotty.shotty.domain.account.domain;

import com.shotty.shotty.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "balances")
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
}
