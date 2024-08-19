package com.shotty.shotty;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;
    private final S3ImageService s3ImageService;

    @PostMapping("/files")
    public String saveFile(@RequestParam("file") MultipartFile file,
                           @RequestParam("desc") String description) {
        fileService.saveFile(file);
        return "업로드 성공! 파일 이름: " + file.getOriginalFilename() + ", 파일 설명: " + description;
    }

    @PostMapping("/s3/upload")
    public ResponseEntity<String> uploadS3(@RequestParam("file") MultipartFile file) {
        String profileImage = s3ImageService.upload(file);

        return ResponseEntity.ok(profileImage);
    }
}
