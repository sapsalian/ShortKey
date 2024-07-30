package com.shotty.shotty.service;

import com.shotty.shotty.Domain.User;
import com.shotty.shotty.exception.LoginFailureException;
import com.shotty.shotty.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class LoginService {
    private final UserRepository userRepository;
    public User login(String userId, String password) {
        //db조회
        User user = userRepository.findByUserId(userId).orElseThrow(() -> new LoginFailureException("존재하지 않는 id입니다."));
        //로그인 실패
        if(!user.getPassword().equals(password)) {
            throw new LoginFailureException("로그인실패");
        }

        return user;
    }


}
