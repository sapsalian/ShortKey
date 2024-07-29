package com.shotty.shotty;

import com.shotty.shotty.service.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.shotty.shotty.Domain.UserRole.ADVERTISER;

@Component
@RequiredArgsConstructor
public class TestDb {
    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.doinit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final UserService userService;

        public void doinit() {
            userService.joinUser("test", "test", ADVERTISER);
        }

    }
}
