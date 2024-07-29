package com.shotty.shotty.service;

import com.shotty.shotty.Domain.User;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.shotty.shotty.Domain.UserRole.ADVERTISER;

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