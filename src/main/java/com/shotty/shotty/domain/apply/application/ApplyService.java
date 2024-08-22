package com.shotty.shotty.domain.apply.application;

import com.shotty.shotty.domain.apply.dao.ApplyRepository;
import com.shotty.shotty.domain.apply.domain.Apply;
import com.shotty.shotty.domain.apply.dto.ApplyPatchRequestDto;
import com.shotty.shotty.domain.apply.dto.ApplyRequestDto;
import com.shotty.shotty.domain.apply.dto.ApplyResponseDto;
import com.shotty.shotty.domain.apply.dto.ApplySearchResponseDto;
import com.shotty.shotty.domain.apply.exception.custom_exception.AlreadyApplyException;
import com.shotty.shotty.domain.apply.exception.custom_exception.ExpiredPostException;
import com.shotty.shotty.domain.bid.application.BidService;
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
import com.shotty.shotty.global.file.S3ImageService;
import com.shotty.shotty.global.util.PatchUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ApplyService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final ApplyRepository applyRepository;
    private final InfluencerRepository influencerRepository;

    private final BidService bidService;
    private final S3ImageService s3ImageService;

    public ApplyResponseDto apply(Long user_id, Long post_id , ApplyRequestDto applyRequestDto) {
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

        return ApplyResponseDto.from(apply);
    }

    public ApplyResponseDto patch(Long user_id, Long apply_id , ApplyPatchRequestDto applyRequestDto) {
        Apply apply = applyRepository.findById(apply_id).orElseThrow(
                () -> new NoSuchResourcException("존재하지 않는 지원Id")
        );
        Long applier_user_id = apply.getInfluencer().getUser().getId();//###쿼리 개수 확인
        if (!applier_user_id.equals(user_id)) {
            throw new PermissionException("지원자 본인만 지원 내용을 수정할 수 있습니다.");
        }

        String original_videoLink = apply.getVideoLink();
        if(!original_videoLink.equals(applyRequestDto.getVideoLink())) {
            imageDelete(original_videoLink);
        }

        PatchUtil.applyPatch(apply,applyRequestDto);

        return ApplyResponseDto.from(apply);
    }

    public ApplySearchResponseDto findApply(Long applyId, Long requesterId) {
        Apply apply = applyRepository.findById(applyId).orElseThrow(
                () -> new NoSuchResourcException("존재하지 않는 지원내역입니다.")
        );

        Long influencerId = apply.getInfluencer().getUser().getId();
        Long advertiserId = apply.getPost().getAuthor().getId();

        if (!influencerId.equals(requesterId) && !advertiserId.equals(requesterId)) {
            throw new PermissionException("지원 내역에 대한 접근 권한이 없습니다.");
        }

        return ApplySearchResponseDto.from(apply);
    }

    public List<ApplySearchResponseDto> findAppliesByInfluencerId(Long influencer_id) {
        influencerRepository.findById(influencer_id).orElseThrow(
                () -> new InfluencerNotFoundException()
        );

        List<Apply> applies = applyRepository.findAllByInfluencerId(influencer_id);

        return applies.stream().map(ApplySearchResponseDto::from).toList();
    }

    public List<ApplySearchResponseDto> findAppliesByUserId(Long user_id) {
        Influencer influencer = influencerRepository.findByUserId(user_id).orElseThrow(
                () -> new InfluencerNotFoundException("인플루언서로 등록된 유저가 아닙니다")
        );

        List<Apply> applies = applyRepository.findAllByInfluencerId(influencer.getId());

        return applies.stream().map(ApplySearchResponseDto::from).toList();
    }

    public void cancel(Long user_id, Long apply_id) {
        Apply apply = applyRepository.findById(apply_id).orElseThrow(
                () -> new NoSuchResourcException("존재하지 않는 지원Id")
        );
        Long applier_user_id = apply.getInfluencer().getUser().getId();//###쿼리 개수 확인
        if (!applier_user_id.equals(user_id)) {
            throw new PermissionException("지원자 본인만 지원을 취소할 수 있습니다.");
        }
        applyRepository.delete(apply);
    }

    public void deleteByInfluencerId(Long influencer_id) {
        bidService.deleteByInfluencerId(influencer_id);
        applyRepository.deleteByInfluencerId(influencer_id);
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

    //S3에 저장된 이미지 삭제
    private void imageDelete(String profile_image) {s3ImageService.deleteImageFromS3(profile_image);}
}
