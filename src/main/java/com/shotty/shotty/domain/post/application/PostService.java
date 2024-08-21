package com.shotty.shotty.domain.post.application;

import com.shotty.shotty.domain.apply.dao.ApplyRepository;
import com.shotty.shotty.domain.apply.domain.Apply;
import com.shotty.shotty.domain.influencer.dao.InfluencerRepository;
import com.shotty.shotty.domain.influencer.domain.Influencer;
import com.shotty.shotty.domain.post.dao.PostRepository;
import com.shotty.shotty.domain.post.domain.Post;
import com.shotty.shotty.domain.post.dto.*;
import com.shotty.shotty.domain.post.dto.PostPatchDto;
import com.shotty.shotty.domain.post.dto.PostRequestDto;
import com.shotty.shotty.domain.post.dto.PostResponseDto;
import com.shotty.shotty.domain.user.dao.UserRepository;
import com.shotty.shotty.domain.user.domain.User;
import com.shotty.shotty.domain.user.dto.EncryptedUserDto;
import com.shotty.shotty.domain.user.exception.custom_exception.UserNotFoundException;
import com.shotty.shotty.global.common.exception.custom_exception.NoSuchResourcException;
import com.shotty.shotty.global.common.exception.custom_exception.NoSuchSortFieldException;
import com.shotty.shotty.global.common.exception.custom_exception.PermissionException;
import com.shotty.shotty.global.util.PatchUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ApplyRepository applyRepository;
    private final InfluencerRepository influencerRepository;

    public PostResponseDto save(long authorId, @Valid PostRequestDto postRequestDto) {
        User user = userRepository.findById(authorId).orElseThrow(() -> new UserNotFoundException("작성자는 존재하지 않는 유저입니다."));

        Post post = Post.of(postRequestDto, user);

        post = postRepository.save(post);

        return PostResponseDto.from(post);
    }

    public Page<PostResponseDto> findAll(Pageable pageable) {
        Page<Post> posts = null;

        try {
            posts = postRepository.findAll(pageable);
        } catch (PropertyReferenceException e) {
            throw new NoSuchSortFieldException();
        }

        return posts.map(PostResponseDto::from);
    }

    public Page<PostResponseDto> findAllByUserId(Long userId, Pageable pageable) {
        Page<Post> posts = null;

        try {
            posts = postRepository.findAllByAuthorId(userId, pageable);
        } catch (PropertyReferenceException e) {
            throw new NoSuchSortFieldException();
        }

        return posts.map(PostResponseDto::from);
    }


    public PostResponseDto edit(Long postId, PostPatchDto postPatchDto, Long userId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NoSuchResourcException("존재하지 않는 공고입니다."));

        if (!post.getAuthor().getId().equals(userId)) {
            throw new PermissionException("공고에 대한 수정권한이 없는 사용자입니다.");
        }

        PatchUtil.applyPatch(post, postPatchDto);
        post = postRepository.save(post);

        return PostResponseDto.from(post);
    }

    public void softDelete(Long postId, Long userId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NoSuchResourcException("존재하지 않는 공고입니다."));

        if (!post.getAuthor().getId().equals(userId)) {
            throw new PermissionException("해당 공고에 대한 삭제권한이 없는 사용자입니다.");
        }

        post.deactivate();
        post = postRepository.save(post);
    }

    public PostDetailResDto findById(Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchResourcException("존재하지 않는 공고입니다."));

        if (userId == null){
            return PostDetailResDto.of(post, null);
        }

        Influencer influencer = influencerRepository.findByUserId(userId)
                .orElse(null);

        Apply apply = null;
        if (influencer != null) {
            apply = applyRepository.findByInfluencerId(influencer.getId())
                    .orElse(null);
        }

        return PostDetailResDto.of(post, apply);
    }

    public void deleteAllByUserId(Long userId) {
        User dummy = getDummy();

        postRepository.updateAllByUserId(userId, dummy.getId());
        postRepository.deactivateAllByUserId(userId);
    }

    private User getDummy() {
        return userRepository.findByName("탈퇴한 사용자 그룹")
                .orElseGet(this::createDummy);
    }

    private User createDummy() {
        EncryptedUserDto encryptedUserDto = new EncryptedUserDto(
                "imDummy1234",
                "dummyPW123",
                "탈퇴한 사용자 그룹",
                true,
                ""
        );

        return userRepository.save(User.from(encryptedUserDto));
    }


    public PostResponseDto update(Long postId, PostRequestDto postRequestDto, Long userId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NoSuchResourcException("존재하지 않는 공고입니다."));

        if (!post.getAuthor().getId().equals(userId)) {
            throw new PermissionException("공고에 대한 수정권한이 없는 사용자입니다.");
        }

        PatchUtil.applyPatch(post, postRequestDto);
        post = postRepository.save(post);

        return PostResponseDto.from(post);
    }
}
