package com.shotty.shotty.exception.handler;

import com.shotty.shotty.dto.common.ResponseDto;
import com.shotty.shotty.exception.custom_exception.auth.InvalidRefreshTokenException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class JwtExceptionHandler {
    @ExceptionHandler(value = ExpiredJwtException.class)
    public ResponseEntity<ResponseDto<Void>> handleExpiredJwtException (){
        ResponseDto<Void> responseDto = new ResponseDto<>(4002, "만료된 토큰", null);
        return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(value = InvalidRefreshTokenException.class)
    public ResponseEntity<ResponseDto<Void>> handleInvalidRefreshTokenException (InvalidRefreshTokenException e){
        ResponseDto<Void> responseDto = new ResponseDto<>(4003, e.getMessage(), null);
        return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
    }
}


