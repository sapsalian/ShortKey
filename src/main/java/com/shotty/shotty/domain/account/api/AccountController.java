package com.shotty.shotty.domain.account.api;

import com.shotty.shotty.domain.account.application.AccountService;
import com.shotty.shotty.domain.account.domain.Account;
import com.shotty.shotty.domain.account.dto.AccountCreateReqDto;
import com.shotty.shotty.domain.account.dto.AccountResDto;
import com.shotty.shotty.global.common.custom_annotation.annotation.TokenId;
import com.shotty.shotty.global.common.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @Operation(summary = "출금 계좌 등록")
    @PostMapping("/api/accounts")
    public ResponseEntity<ResponseDto<AccountResDto>> createAccount(@TokenId Long userId, @RequestBody AccountCreateReqDto accountCreateReqDto) {
        AccountResDto accountResDto = accountService.createAccount(accountCreateReqDto, userId);

        ResponseDto<AccountResDto> responseDto = new ResponseDto<>(
            2001,
                "계좌 등록 성공",
                accountResDto
        );

        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "내 출금 계좌 조회")
    @GetMapping("/api/accounts")
    public ResponseEntity<ResponseDto<AccountResDto>> getAccounts(@TokenId Long userId) {
        AccountResDto accountResDto = accountService.getAccountOf(userId);

        ResponseDto<AccountResDto> responseDto = new ResponseDto<>(
                2002,
                "출금 계좌 정보 조회 완료",
                accountResDto
        );

        return ResponseEntity.ok(responseDto);
    }
}
