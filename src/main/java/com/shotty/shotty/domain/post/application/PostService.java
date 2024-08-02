package com.shotty.shotty.domain.post.application;

import com.shotty.shotty.domain.post.dao.PostRepository;
import com.shotty.shotty.domain.post.domain.Post;
import com.shotty.shotty.domain.post.dto.ImgContainedPostDto;
import com.shotty.shotty.domain.post.dto.PostRequestDto;
import com.shotty.shotty.domain.post.dto.PostResponseDto;
import com.shotty.shotty.domain.user.application.UserService;
import com.shotty.shotty.domain.user.dao.UserRepository;
import com.shotty.shotty.domain.user.domain.User;
import com.shotty.shotty.domain.user.exception.custom_exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostResponseDto save(long authorId, PostRequestDto postRequestDto) {
        String imgUrl = imageSave();
        ImgContainedPostDto imgContainedPostDto = ImgContainedPostDto.of(postRequestDto, imgUrl);

        User user = userRepository.findById(authorId).orElseThrow(() -> new UserNotFoundException("작성자는 존재하지 않는 유저입니다."));

        Post post = Post.of(imgContainedPostDto, user);

        post = postRepository.save(post);

        return PostResponseDto.from(post);
    }

    // TODO: S3 이용해 image 저장하고 url 반환하는 메서드
    private String imageSave() {
        return "";
    }


}
