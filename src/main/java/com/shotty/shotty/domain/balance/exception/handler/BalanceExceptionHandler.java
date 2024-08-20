package com.shotty.shotty.domain.balance.exception.handler;

import com.shotty.shotty.domain.balance.exception.custom_exception.NotEnoughBalanceException;
import com.shotty.shotty.global.common.dto.ResponseDto;
import jakarta.validation.constraints.Null;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BalanceExceptionHandler {
    @ExceptionHandler(NotEnoughBalanceException.class)
    public ResponseEntity<ResponseDto<Null>> handleNotEnoughBalanceException(NotEnoughBalanceException exception) {
        ResponseDto<Null> responseDto = new ResponseDto<>(
                4005,
                exception.getMessage(),
                null
        );

        return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
    }
}
