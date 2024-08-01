package com.shotty.shotty.domain.influencer.application;

import com.shotty.shotty.domain.influencer.dao.InfluencerRepository;
import com.shotty.shotty.domain.influencer.domain.Influencer;
import com.shotty.shotty.domain.influencer.dto.SaveInfluencerDto;
import com.shotty.shotty.domain.influencer.exception.custom_exception.AlreadyInfluencerException;
import com.shotty.shotty.domain.user.application.UserService;
import com.shotty.shotty.domain.user.dao.UserRepository;
import com.shotty.shotty.domain.user.domain.User;
import com.shotty.shotty.domain.user.exception.custom_exception.UserNotFoundException;
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

    public void register(Long user_id,SaveInfluencerDto saveInfluencerDto) {
        User user = userRepository.findById(user_id).orElseThrow(
                ()->{throw new UserNotFoundException();}
        );

        influencerRepository.findByUserId(user_id).ifPresent(
                influencer->{throw new AlreadyInfluencerException();}
        );

        Influencer influencer = Influencer.from(user, saveInfluencerDto);
        influencerRepository.save(influencer);
    }


}
