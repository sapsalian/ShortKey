package com.shotty.shotty.global.file;

public class S3Exception extends RuntimeException {
    public S3Exception() {}
    public S3Exception(String message) {
        super(message);
    }
}
