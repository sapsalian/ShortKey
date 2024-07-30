package com.shotty.shotty.Domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name="user_id")
    private String userId;

    private String password;

    private String name;

    private LocalDateTime created_at;

    @Enumerated(EnumType.STRING)
    private UserRole role;



    public static User createUser(String userId, String password, UserRole role) {
        User user = new User();
        user.userId = userId;
        user.password = password;
        user.role = role;
        return user;
    }

}
