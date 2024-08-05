package com.shotty.shotty.global.common.exception.handler;

import com.shotty.shotty.domain.user.exception.custom_exception.UserNotFoundException;
import com.shotty.shotty.global.common.dto.ResponseDto;
import com.shotty.shotty.global.common.exception.custom_exception.NoSuchResourcException;
import com.shotty.shotty.global.common.exception.custom_exception.NoSuchSortFieldException;
import jakarta.validation.constraints.Null;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NoSuchSortFieldException.class)
    public ResponseEntity<ResponseDto<Null>> handleNoSuchSortFieldException(NoSuchSortFieldException e) {
        ResponseDto<Null> responseDto = new ResponseDto<>(
                4004,
                "요청하신 sort 쿼리 파라미터 값과 일치하는 필드가 존재하지 않습니다.",
                null
        );

        return ResponseEntity.badRequest().body(responseDto);
    }

    @ExceptionHandler(NoSuchResourcException.class)
    public ResponseEntity<ResponseDto<Null>> handleNoSuchResourcException(NoSuchResourcException e) {
        ResponseDto<Null> responseDto = new ResponseDto<>(
                4041,
                e.getMessage(),
                null
        );

        return new ResponseEntity<>(responseDto, HttpStatus.NOT_FOUND);
    }
}
