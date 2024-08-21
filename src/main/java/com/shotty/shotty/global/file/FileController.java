package com.shotty.shotty.global.file;

import com.shotty.shotty.global.common.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "파일업로드 API")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FileController {
    private final S3ImageService s3ImageService;

    @Operation(summary = "파일 업로드", description = "요청 받은 파일을 s3에 업로드")
    @PostMapping(value = "/files",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseDto<String>> uploadS3(@RequestParam("file") MultipartFile file) {
        String url = s3ImageService.upload(file);
        ResponseDto<String> response = new ResponseDto<>(
                2000,
                "파일 업로드 성공",
                url
        );

        return ResponseEntity.ok(response);
    }
}
