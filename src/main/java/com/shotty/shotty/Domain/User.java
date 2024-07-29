package com.shotty.shotty.Domain;

import com.shotty.shotty.dto.EncryptedUserDto;
import com.shotty.shotty.dto.ResisterRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name="user_id", unique=true)
    private String userId;

    private String password;

    private String name;

    @CreatedDate
    private LocalDateTime created_at;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    public User() {}

    private User(String userId, String password, String name, UserRole role) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.role = role;
    }

    public static User createUser(String email, String password, String name, UserRole role) {
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
                encryptedUserDto.userRole()
        );
    }

}
