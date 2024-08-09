package com.shotty.shotty.domain.influencer.application;

import com.shotty.shotty.domain.apply.application.ApplyService;
import com.shotty.shotty.domain.influencer.dao.InfluencerRepository;
import com.shotty.shotty.domain.influencer.domain.Influencer;
import com.shotty.shotty.domain.influencer.domain.InfluencerPatch;
import com.shotty.shotty.domain.influencer.dto.ResponseInfluencerDto;
import com.shotty.shotty.domain.influencer.dto.SaveInfluencerDto;
import com.shotty.shotty.domain.influencer.exception.custom_exception.AlreadyInfluencerException;
import com.shotty.shotty.domain.influencer.exception.custom_exception.InfluencerNotFoundException;
import com.shotty.shotty.domain.user.dao.UserRepository;
import com.shotty.shotty.domain.user.domain.User;
import com.shotty.shotty.domain.user.exception.custom_exception.UserNotFoundException;
import com.shotty.shotty.global.util.PatchUtil;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Getter
@Service
@Transactional
@RequiredArgsConstructor
public class InfluencerService {
    private final InfluencerRepository influencerRepository;
    private final UserRepository userRepository;
    private final ApplyService applyService;

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


    public Page<ResponseInfluencerDto> findAllInfluencers(Pageable pageable) {
        Page<Influencer> influencers = influencerRepository.findAll(pageable);
        if(influencers.isEmpty()) throw new InfluencerNotFoundException();

        List<ResponseInfluencerDto> dtos = influencers.stream()
                .map(ResponseInfluencerDto::from)
                .toList();

        // Page로 변환
        Page<ResponseInfluencerDto> dtoPage = new PageImpl<>(dtos, pageable, influencers.getTotalElements());

        return dtoPage;
    }

    public ResponseInfluencerDto findOne(Long influencerId) {
        Influencer influencer = influencerRepository.findById(influencerId).orElseThrow(
                () -> {throw new InfluencerNotFoundException();}
        );
        return ResponseInfluencerDto.from(influencer);
    }

    public ResponseInfluencerDto patch(Long influencerId, InfluencerPatch influencerPatch) {
        Influencer influencer = influencerRepository.findById(influencerId).orElseThrow(
                () -> {
                    throw new InfluencerNotFoundException();
                }
        );
        PatchUtil.applyPatch(influencer,influencerPatch);

        return ResponseInfluencerDto.from(influencer);
    }

    public void deleteByUserId(Long userId) {
        influencerRepository.findByUserId(userId).ifPresent(
                (influencer) -> {
                    applyService.deleteByInfluencerId(influencer.getId());
                    influencerRepository.delete(influencer);
                }
        );
    }
}
