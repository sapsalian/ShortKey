package com.shotty.shotty.domain.apply.application;

import com.shotty.shotty.domain.apply.dao.ApplyRepository;
import com.shotty.shotty.domain.apply.domain.Apply;
import com.shotty.shotty.domain.apply.dto.ApplyRequestDto;
import com.shotty.shotty.domain.apply.dto.ApplyResponseDto;
import com.shotty.shotty.domain.apply.exception.custom_exception.AlreadyApplyException;
import com.shotty.shotty.domain.apply.exception.custom_exception.ExpiredPostException;
import com.shotty.shotty.domain.influencer.dao.InfluencerRepository;
import com.shotty.shotty.domain.influencer.domain.Influencer;
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
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class ApplyService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final ApplyRepository applyRepository;
    public ApplyResponseDto apply(Long user_id,Long post_id ,ApplyRequestDto applyRequestDto) {
        Influencer influencer = getInfluencer(user_id);
        applyRepository.findByInfluencerId(influencer.getId()).ifPresent(
                Apply->{throw new AlreadyApplyException("해당 공고에 이미 지원하였습니다.");}
        );
        Post post = getPost(post_id);
        Apply apply = Apply.from(applyRequestDto, influencer, post);

        applyRepository.save(apply);

        return ApplyResponseDto.from(apply);
    }

    private Post getPost(Long post_id) {
        Post post = postRepository.findById(post_id).orElseThrow(
                () -> {throw new NoSuchResourcException("해당 공고가 존재 하지 않습니다.");}
        );
        if (!post.isActive()) {
            throw new ExpiredPostException("만료된 공고입니다.");
        }
        return post;
    }

    private Influencer getInfluencer(Long user_id) {
        User user = userRepository.findById(user_id).orElseThrow(
                () -> {throw new UserNotFoundException("등록되지 않은 유저입니다.");}
        );
        if(user.getRole() != UserRoleEnum.INFLUENCER){
            throw new PermissionException("인플루언서로 등록된 유저가 아닙니다.");//###
        }

        return user.getInfluencer();
    }
}
