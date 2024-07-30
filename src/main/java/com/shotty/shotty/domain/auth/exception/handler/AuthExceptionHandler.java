package com.shotty.shotty.domain.auth.exception.handler;

import com.shotty.shotty.global.common.dto.ResponseDto;
import com.shotty.shotty.domain.auth.exception.custom_exception.user.LoginFailureException;
import com.shotty.shotty.domain.auth.exception.custom_exception.user.UserIdDuplicateException;
import jakarta.validation.constraints.Null;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class AuthExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();

            errors.merge(fieldName, errorMessage, (existingMessage, newMessage) -> existingMessage + "\n" + newMessage);
        });

        ResponseDto responseDto = new ResponseDto(
                (short)4000,
                "요청 필드가 유효하지 않습니다.",
                errors
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
    }

    @ExceptionHandler(UserIdDuplicateException.class)
    public ResponseEntity<ResponseDto> handleUserIdDuplicateException(UserIdDuplicateException exception) {
        ResponseDto responseDto = new ResponseDto(
                (short)4090,
                "이미 존재하는 사용자입니다.",
                null
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(responseDto);
    }

    @ExceptionHandler(LoginFailureException.class)
    public ResponseEntity<ResponseDto<Null>> handleLoginFailureException(LoginFailureException exception) {
        ResponseDto<Null> responseDto = new ResponseDto<>(
                4001,
                "올바르지 않은 id 또는 비밀번호입니다.",
                null
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
    }
}
