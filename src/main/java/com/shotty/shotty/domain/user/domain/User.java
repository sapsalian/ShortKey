package com.shotty.shotty.domain.user.domain;

import com.shotty.shotty.domain.influencer.domain.Influencer;
import com.shotty.shotty.domain.user.dto.EncryptedUserDto;
import com.shotty.shotty.domain.user.enums.UserRoleEnum;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

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

    @CreatedDate
    private LocalDateTime created_at;

    private String email;

    @Enumerated(EnumType.STRING)
    private UserRoleEnum role;

    @OneToOne(mappedBy = "user",fetch = FetchType.LAZY)
    private Influencer influencer;


    public User() {}

    private User(String userId, String password, String name, UserRoleEnum role) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.role = role;
    }

    public static User createUser(String email, String password, String name, UserRoleEnum role) {
        User user = new User();
        user.userId = email;
        user.password = password;
        user.role = role;
        return user;
    }

    public static User from(EncryptedUserDto encryptedUserDto) {
        return new User(
                encryptedUserDto.userId(),
                encryptedUserDto.encryptedPassword(),
                encryptedUserDto.userName(),
                encryptedUserDto.userRoleEnum()
        );
    }

    public void changeRole(UserRoleEnum userRoleEnum) {
        role = userRoleEnum;
    }
}
