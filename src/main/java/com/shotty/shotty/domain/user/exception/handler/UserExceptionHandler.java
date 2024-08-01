package com.shotty.shotty.domain.user.exception.handler;

import com.shotty.shotty.domain.user.exception.custom_exception.UserNotFoundException;
import com.shotty.shotty.global.common.dto.ResponseDto;
import jakarta.validation.constraints.Null;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ResponseDto<Null>> handleUserNotFoundException(UserNotFoundException e) {
        ResponseDto<Null> responseDto = new ResponseDto<>(
                4040,
                "등록된 회원이 아닙니다",
                null
        );
        return ResponseEntity.status(404).body(responseDto);
    }
}
