package com.shotty.shotty;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {
    public void saveFile(MultipartFile file) {
        if (!file.isEmpty()) {
            Path filepath = Paths.get("image", file.getOriginalFilename());

            log.info("filepath = {}",filepath);
            try (OutputStream os = Files.newOutputStream(filepath)){
                os.write(file.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
