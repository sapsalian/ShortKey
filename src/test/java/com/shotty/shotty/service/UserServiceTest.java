package com.shotty.shotty.service;

import com.shotty.shotty.domain.auth.domain.User;
import com.shotty.shotty.domain.auth.application.UserService;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.shotty.shotty.domain.auth.enums.UserRoleEnum.ADVERTISER;

@SpringBootTest
@Transactional
public class UserServiceTest {
    @Autowired
    private UserService userService;
    @Test
    public void 회원가입() throws Exception{
        //given
        Long joinId = userService.joinUser("asdf", "asdf", ADVERTISER);

        //when
        User user = userService.findOneById(joinId);

        //then
        Assertions.assertThat(user.getId()).isEqualTo(joinId);

    }
}