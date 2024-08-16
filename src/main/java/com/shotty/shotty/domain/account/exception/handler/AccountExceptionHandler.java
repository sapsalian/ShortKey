package com.shotty.shotty.domain.account.exception.handler;

import com.shotty.shotty.domain.account.exception.custom_exception.NoBankAccountException;
import com.shotty.shotty.global.common.dto.ResponseDto;
import jakarta.validation.constraints.Null;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AccountExceptionHandler {
    @ExceptionHandler(NoBankAccountException.class)
    public ResponseEntity<ResponseDto<Null>> noBankAccountHandler(NoBankAccountException e) {
        ResponseDto<Null> responseDto = new ResponseDto<>(
                4042,
                "계좌가 등록되어 있지 않습니다. 계좌를 먼저 등록해주세요.",
                null
        );

        return new ResponseEntity<>(responseDto, HttpStatus.NOT_FOUND);
    }
}
