package com.shotty.shotty;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
public class FileUpload {
    public String getFullPath(String filename, String fileDir) {
        return fileDir + filename;
    }

    public String serverUploadFile(MultipartFile multipartFile, String fileDir) throws IOException {
        String originalFilename = multipartFile.getOriginalFilename();
        String serverUploadFileName = createServerFileName(originalFilename);
        multipartFile.transferTo(new File(getFullPath(serverUploadFileName,fileDir)));

        return serverUploadFileName;
    }

    private String createServerFileName(String originalFilename) {
        String uuid = UUID.randomUUID().toString();
        String ext = extractExt(originalFilename);
        return uuid + "." + ext;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos+1);
    }
}
