package com.shotty.shotty.domain.apply.application;

import com.shotty.shotty.domain.apply.dao.ApplyRepository;
import com.shotty.shotty.domain.apply.domain.Apply;
import com.shotty.shotty.domain.apply.dto.ApplyRequestDto;
import com.shotty.shotty.domain.apply.dto.ApplyRegisterResponseDto;
import com.shotty.shotty.domain.apply.dto.ApplySearchResponseDto;
import com.shotty.shotty.domain.apply.exception.custom_exception.AlreadyApplyException;
import com.shotty.shotty.domain.apply.exception.custom_exception.ExpiredPostException;
import com.shotty.shotty.domain.influencer.dao.InfluencerRepository;
import com.shotty.shotty.domain.influencer.domain.Influencer;
import com.shotty.shotty.domain.influencer.exception.custom_exception.InfluencerNotFoundException;
import com.shotty.shotty.domain.post.dao.PostRepository;
import com.shotty.shotty.domain.post.domain.Post;
import com.shotty.shotty.domain.user.dao.UserRepository;
import com.shotty.shotty.domain.user.domain.User;
import com.shotty.shotty.domain.user.enums.UserRoleEnum;
import com.shotty.shotty.domain.user.exception.custom_exception.UserNotFoundException;
import com.shotty.shotty.global.common.exception.custom_exception.NoSuchResourcException;
import com.shotty.shotty.global.common.exception.custom_exception.PermissionException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ApplyService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final ApplyRepository applyRepository;
    private final InfluencerRepository influencerRepository;

    public ApplyRegisterResponseDto apply(Long user_id, Long post_id , ApplyRequestDto applyRequestDto) {
        Influencer influencer = getInfluencer(user_id);
        //지원 여부 확인
        applyRepository.findByInfluencerIdAndPostId(influencer.getId(),post_id).ifPresent(
                apply->{
                    if(apply.getPost().getId().equals(post_id))
                        throw new AlreadyApplyException("해당 공고에 이미 지원하였습니다.");
                }
        );
        Post post = getPost(post_id);
        Apply apply = Apply.from(applyRequestDto, influencer, post);

        applyRepository.save(apply);

        return ApplyRegisterResponseDto.from(apply);
    }

    public List<ApplySearchResponseDto> findAppliesByInfluencerId(Long influencer_id) {
        influencerRepository.findById(influencer_id).orElseThrow(
                () -> new InfluencerNotFoundException()
        );

        List<Apply> applies = applyRepository.findAllByInfluencerId(influencer_id);

        return applies.stream().map(ApplySearchResponseDto::from).toList();
    }



    private Post getPost(Long post_id) {
        Post post = postRepository.findById(post_id).orElseThrow(
                () -> new NoSuchResourcException("해당 공고가 존재 하지 않습니다.")
        );
        if (!post.isActive()) {
            throw new ExpiredPostException("만료된 공고입니다.");
        }
        return post;
    }

    private Influencer getInfluencer(Long user_id) {
        User user = userRepository.findById(user_id).orElseThrow(
                () -> new UserNotFoundException("등록되지 않은 유저입니다.")
        );
        if(user.getRole() != UserRoleEnum.INFLUENCER){
            throw new PermissionException("인플루언서로 등록된 유저가 아닙니다.");//###
        }

        return user.getInfluencer();
    }
}
