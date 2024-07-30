package com.shotty.shotty.service;

import com.shotty.shotty.Domain.User;
import com.shotty.shotty.Domain.UserRole;
import com.shotty.shotty.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Long joinUser(String userId, String password, UserRole userRole) {
        User user = User.createUser(userId, password, userRole);
        userRepository.save(user);
        return user.getId();
    }
//
//    public User findOneById(Long id) {
//        return userRepository.findById(id);
//    }
//
//    public User findOneByEmail(String email) {
//        return userRepository.findByUserId(email);
//    }
}
