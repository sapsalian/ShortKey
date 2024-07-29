package com.shotty.shotty.Domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
public class User {
    @Id
    @GeneratedValue
    @Column(name="user_id")
    private Long id;

    private String email;
    private String password;

    private LocalDateTime created_at;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    public static User createUser(String email, String password, UserRole role) {
        User user = new User();
        user.email = email;
        user.password = password;
        user.role = role;
        return user;
    }

}
