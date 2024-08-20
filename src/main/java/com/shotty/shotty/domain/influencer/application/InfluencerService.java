package com.shotty.shotty.domain.influencer.application;

import com.shotty.shotty.global.file.S3ImageService;
import com.shotty.shotty.domain.apply.application.ApplyService;
import com.shotty.shotty.domain.influencer.dao.InfluencerRepository;
import com.shotty.shotty.domain.influencer.domain.Influencer;
import com.shotty.shotty.domain.influencer.domain.InfluencerPatch;
import com.shotty.shotty.domain.influencer.dto.*;
import com.shotty.shotty.domain.influencer.exception.custom_exception.AlreadyInfluencerException;
import com.shotty.shotty.domain.influencer.exception.custom_exception.InfluencerNotFoundException;
import com.shotty.shotty.domain.user.dao.UserRepository;
import com.shotty.shotty.domain.user.domain.User;
import com.shotty.shotty.domain.user.enums.UserRoleEnum;
import com.shotty.shotty.domain.user.exception.custom_exception.UserNotFoundException;
import com.shotty.shotty.global.common.exception.custom_exception.PermissionException;
import com.shotty.shotty.global.util.PatchUtil;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Service
@Transactional
@RequiredArgsConstructor
public class InfluencerService {
    private final InfluencerRepository influencerRepository;
    private final UserRepository userRepository;
    private final ApplyService applyService;
    private final S3ImageService s3ImageService;

    public ResponseInfluencerDto register(Long user_id, RegisterInfluencerDto registerInfluencerDto) {
        User user = getUser(user_id);

        SaveInfluencerDto saveInfluencerDto = SaveInfluencerDto.from(registerInfluencerDto);
        Influencer influencer = Influencer.of(user, saveInfluencerDto);
        influencerRepository.save(influencer);
        return ResponseInfluencerDto.from(influencer);
    }

    public Page<ResponseInfluencerDto> findAllInfluencers(Pageable pageable,InfluencerSearchInfo influencerSearchInfo) {
        Page<Influencer> influencers = influencerRepository
                .findAll(influencerSearchInfo.getUserName(), influencerSearchInfo.getNiche(),pageable);

        // Page로 변환
        return influencers.map(ResponseInfluencerDto::from);
    }

    public ResponseInfluencerDto findOne(Long influencerId) {
        Influencer influencer = influencerRepository.findById(influencerId).orElseThrow(
                () -> {throw new InfluencerNotFoundException();}
        );
        return ResponseInfluencerDto.from(influencer);
    }

    public ResponseInfluencerDto update(Long user_id,Long influencer_id, InfluencerUpdateRequestDto influencerUpdateRequestDto) {
        Influencer influencer = getInfluencer(user_id, influencer_id);

        InfluencerPatch influencerPatch = InfluencerPatch.from(influencerUpdateRequestDto);

        PatchUtil.applyPatch(influencer,influencerPatch);

        return ResponseInfluencerDto.from(influencer);
    }

    public void deleteByUserId(Long userId) {
        influencerRepository.findByUserId(userId).ifPresent(
                (influencer) -> {
                    applyService.deleteByInfluencerId(influencer.getId());
                    deleteInfluencer(influencer);
                }
        );
    }

    //인플루언서 등록 취소
    public void cancel(Long user_id) {
        Influencer influencer = influencerRepository.findByUserId(user_id).orElseThrow(
                () -> new InfluencerNotFoundException("인플루언서로 등록된 회원이 아닙니다")
        );

        applyService.deleteByInfluencerId(influencer.getId());
        influencer.getUser().changeRole(UserRoleEnum.COMMON);

        deleteInfluencer(influencer);
    }

    private Influencer getInfluencer(Long user_id, Long influencer_id) {
        Influencer influencer = influencerRepository.findById(influencer_id).orElseThrow(
                ()->new InfluencerNotFoundException()
        );

        if (!influencer.getUser().getId().equals(user_id)) {
            throw new PermissionException("수정 권한 없음");
        }
        return influencer;
    }

    private void deleteInfluencer(Influencer influencer) {
        //인플루언서 등록 취소시 S3 저장된 프로필 이미지 삭제
        imageDelete(influencer.getProfile_image());
        influencerRepository.delete(influencer);
    }


    private User getUser(Long user_id) {
        User user = userRepository.findById(user_id).orElseThrow(
                ()->{throw new UserNotFoundException();}
        );

        influencerRepository.findByUserId(user_id).ifPresent(
                influencer->{throw new AlreadyInfluencerException();}
        );
        return user;
    }

    //S3에 저장된 이미지 삭제
    private void imageDelete(String profile_image) {
        s3ImageService.deleteImageFromS3(profile_image);

    }

    //S3에 이미지 저장
    private String imageSave(MultipartFile file) {
        return s3ImageService.upload(file);
    }
}
