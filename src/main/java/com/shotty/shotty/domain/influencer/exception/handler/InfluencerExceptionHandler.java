package com.shotty.shotty.domain.influencer.exception.handler;

import com.shotty.shotty.domain.influencer.exception.custom_exception.AlreadyInfluencerException;
import com.shotty.shotty.global.common.dto.ResponseDto;
import jakarta.validation.constraints.Null;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class InfluencerExceptionHandler {
    @ExceptionHandler(AlreadyInfluencerException.class)
    public ResponseEntity<ResponseDto<Null>> handleAlreadyInfluencerException() {
        ResponseDto<Null> responseDto = new ResponseDto<>(
                4093,
                "이미 인플루언서로 등록된 유저",
                null
        );
        return ResponseEntity.status(409).body(responseDto);
    }

}
