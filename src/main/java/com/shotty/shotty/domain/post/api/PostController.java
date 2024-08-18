package com.shotty.shotty.domain.post.api;

import com.shotty.shotty.S3ImageService;
import com.shotty.shotty.domain.post.application.PostService;
import com.shotty.shotty.domain.post.dto.PostPatchDto;
import com.shotty.shotty.domain.post.dto.PostRequestDto;
import com.shotty.shotty.domain.post.dto.PostResponseDto;
import com.shotty.shotty.global.common.custom_annotation.annotation.TokenId;
import com.shotty.shotty.global.common.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@Tag(name= "공고 API 처리 컨트롤러",description = "공고 등록, 조회, 수정 삭제 API를 처리하는 컨트롤러")
public class PostController {
    private final PostService postService;

    @PostMapping("/api/posts")
    @Operation(summary = "공고 등록", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<ResponseDto<PostResponseDto>> postPost(
            @Parameter(hidden = true) @TokenId Long authorId,
            @Valid @RequestPart("postInfo") PostRequestDto postRequestDto,
            @RequestPart MultipartFile file) {
        PostResponseDto postResponseDto = postService.save(authorId ,postRequestDto, file);

        ResponseDto<PostResponseDto> responseDto = new ResponseDto<>(
                2011,
                "공고 생성 완료",
                postResponseDto
        );

        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/api/posts")
    @Operation(summary = "공고 전체 조회")
    public ResponseEntity<ResponseDto<Page<PostResponseDto>>> getPosts(
            @ParameterObject @PageableDefault(size = 10, page = 0, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        Page<PostResponseDto> postPage = postService.findAll(pageable);

        ResponseDto<Page<PostResponseDto>> responseDto = new ResponseDto<>(
                2002,
                "공고 전체 조회 성공",
                postPage
        );

        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/api/posts/{postId}")
    @Operation(summary = "공고 개별 조회")
    public ResponseEntity<ResponseDto<PostResponseDto>> getPost(@PathVariable Long postId) {
        PostResponseDto postResponseDto = postService.findById(postId);

        ResponseDto<PostResponseDto> responseDto = new ResponseDto<>(
                2002,
                "공고 개별 조회 성공",
                postResponseDto
        );

        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/api/posts/{postId}")
    @Operation(summary = "공고 수정")
    public ResponseEntity<ResponseDto<PostResponseDto>> updatePost(

            @Parameter(hidden = true) @TokenId Long userId,
            @PathVariable Long postId,
            @Valid @RequestBody PostRequestDto postRequestDto
    ) {
        PostResponseDto postResponseDto = postService.update(postId, postRequestDto, userId);

        ResponseDto<PostResponseDto> responseDto = new ResponseDto<>(
                2003,
                "공고 수정 완료",
                postResponseDto
        );

        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/api/posts/{postId}")
    @Operation(summary = "공고 삭제")
    public ResponseEntity<ResponseDto<Null>> deletePost(
            @Parameter(hidden = true) @TokenId Long userId,
            @PathVariable Long postId
    ) {
        postService.softDelete(postId, userId);

        ResponseDto<Null> responseDto = new ResponseDto<>(
                2006,
                "공고 삭제 완료",
                null
        );

        return ResponseEntity.ok(responseDto);
    }
}
