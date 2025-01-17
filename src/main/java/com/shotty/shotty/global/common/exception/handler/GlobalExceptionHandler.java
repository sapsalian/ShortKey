package com.shotty.shotty.global.common.exception.handler;

import com.shotty.shotty.global.common.dto.ResponseDto;
import com.shotty.shotty.global.common.exception.custom_exception.NoSuchResourcException;
import com.shotty.shotty.global.common.exception.custom_exception.NoSuchSortFieldException;
import com.shotty.shotty.global.common.exception.custom_exception.PermissionException;
import jakarta.validation.constraints.Null;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
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

    @ExceptionHandler(PermissionException.class)
    public ResponseEntity<ResponseDto<Null>> handlePermissionException(PermissionException e) {
        ResponseDto<Null> responseDto = new ResponseDto<>(
                4030,
                e.getMessage(),
                null
        );

        return new ResponseEntity<>(responseDto, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ResponseDto<Null>> handleDuplicateKeyException(DataIntegrityViolationException e) {
        ResponseDto<Null> responseDto = new ResponseDto<>(
                4094,
                e.getMessage(),
                null
        );

        return new ResponseEntity<>(responseDto, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    @Order(Ordered.LOWEST_PRECEDENCE)
    public ResponseEntity<ResponseDto<Null>> handleException(Exception e) {
        ResponseDto<Null> responseDto = new ResponseDto<>(
                5000,
                e.getMessage(),
                null
        );

        return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
