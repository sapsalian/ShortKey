package com.shotty.shotty.domain.user.domain;

import com.shotty.shotty.domain.influencer.domain.Influencer;
import com.shotty.shotty.domain.user.dto.EncryptedUserDto;
import com.shotty.shotty.domain.user.enums.UserRoleEnum;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name="user_id", unique=true)
    private String userId;

    private String password;

    private String name;

    private boolean gender;

    @CreationTimestamp
    private LocalDateTime created_at;

    private String email;

    @Enumerated(EnumType.STRING)
    private UserRoleEnum role;

    private Long balance;

    @OneToOne(mappedBy = "user",fetch = FetchType.LAZY)
    private Influencer influencer;


    public User() {}

    private User(String userId, String password, String name, boolean gender, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.role = UserRoleEnum.COMMON;
        this.balance = 0L;
    }

    public static User from(EncryptedUserDto encryptedUserDto) {
        return new User(
                encryptedUserDto.userId(),
                encryptedUserDto.encryptedPassword(),
                encryptedUserDto.userName(),
                encryptedUserDto.userGender(),
                encryptedUserDto.userEmail()
        );
    }

    public void changeRole(UserRoleEnum userRoleEnum) {
        role = userRoleEnum;
    }

    public void deposit(Long amount) {
        balance += amount;
    }

    public void withdraw(Long amount) {
        balance -= amount;
    }
}
