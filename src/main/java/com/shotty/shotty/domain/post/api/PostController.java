package com.shotty.shotty.domain.post.api;

import com.shotty.shotty.domain.post.application.PostService;
import com.shotty.shotty.domain.post.dto.PostRequestDto;
import com.shotty.shotty.domain.post.dto.PostResponseDto;
import com.shotty.shotty.global.common.custom_annotation.annotation.TokenId;
import com.shotty.shotty.global.common.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name= "공고 API 처리 컨트롤러",description = "공고 등록, 조회, 수정 삭제 API를 처리하는 컨트롤러")
public class PostController {
    private final PostService postService;

    @PostMapping("/api/posts")
    @Operation(summary = "공고 등록", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<ResponseDto<PostResponseDto>> postPost(@Parameter(hidden = true) @TokenId Long authorId, @Valid @RequestBody PostRequestDto postRequestDto) {
        PostResponseDto postResponseDto = postService.save(authorId, postRequestDto);

        ResponseDto<PostResponseDto> responseDto = new ResponseDto<>(
                2011,
                "공고 생성 완료",
                postResponseDto
        );

        return ResponseEntity.ok(responseDto);
    }
}
