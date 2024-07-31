package com.shotty.shotty.domain.influencer.application;

import com.shotty.shotty.domain.influencer.dao.InfluencerRepository;
import com.shotty.shotty.domain.influencer.domain.Influencer;
import com.shotty.shotty.domain.influencer.dto.SaveInfluencerDto;
import com.shotty.shotty.domain.user.application.UserService;
import com.shotty.shotty.domain.user.dao.UserRepository;
import com.shotty.shotty.domain.user.domain.User;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Getter
@Service
@Transactional
@RequiredArgsConstructor
public class InfluencerService {
    private final InfluencerRepository influencerRepository;
    private final UserRepository userRepository;

    public Long register(Long user_id,SaveInfluencerDto saveInfluencerDto) {
        User user = userRepository.findById(user_id).orElseThrow(
                //UserNotFoundException 추가
                ()->{throw new RuntimeException("UserNotFoundException "); }
        );

        Influencer influencer = Influencer.from(user, saveInfluencerDto);
        return influencerRepository.save(influencer).getId();
    }


}
