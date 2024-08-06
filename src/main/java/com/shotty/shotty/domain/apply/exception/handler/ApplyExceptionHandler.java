package com.shotty.shotty.domain.apply.exception.handler;

import com.shotty.shotty.domain.apply.exception.custom_exception.AlreadyApplyException;
import com.shotty.shotty.global.common.dto.ResponseDto;
import jakarta.validation.constraints.Null;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApplyExceptionHandler {
    @ExceptionHandler(value = AlreadyApplyException.class)
    public ResponseEntity<ResponseDto<Null>> handleAlreadyApplyException(AlreadyApplyException e) {
        ResponseDto<Null> responseDto = new ResponseDto<>(
                4094,
                e.getMessage(),
                null
        );
        return ResponseEntity.status(409).body(responseDto);
    }
}
