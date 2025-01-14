package com.shotty.shotty.domain.balance.api;

import com.shotty.shotty.domain.balance.application.BalanceService;
import com.shotty.shotty.domain.balance.dto.BalanceResDto;
import com.shotty.shotty.domain.balance.dto.ChangeBalanceDto;
import com.shotty.shotty.global.common.custom_annotation.annotation.TokenId;
import com.shotty.shotty.global.common.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "충전금 관련 API")
public class BalanceController {
    private final BalanceService balanceService;

    @GetMapping("/api/balance")
    @Operation(summary = "잔액 조회")
    public ResponseEntity<ResponseDto<BalanceResDto>> getBalance(@Parameter(hidden = true) @TokenId Long userId) {
        BalanceResDto balanceResDto = balanceService.getBalanceByUserId(userId);

        ResponseDto<BalanceResDto> responseDto = new ResponseDto<>(
                2002,
                "잔액 조회 성공",
                balanceResDto
        );

        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/api/balance/deposit")
    @Operation(summary = "입금")
    public ResponseEntity<ResponseDto<BalanceResDto>> deposit(@Parameter(hidden = true) @TokenId Long userId, @RequestBody ChangeBalanceDto changeBalanceDto) {
        BalanceResDto balanceResDto = balanceService.deposit(userId, changeBalanceDto);

        ResponseDto<BalanceResDto> responseDto = new ResponseDto<>(
                2007,
                "입금 완료",
                balanceResDto
        );

        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/api/balance/withdraw")
    @Operation(summary = "출금")
    public ResponseEntity<ResponseDto<BalanceResDto>> withdraw(@Parameter(hidden = true) @TokenId Long userId, @RequestBody ChangeBalanceDto changeBalanceDto) {
        BalanceResDto balanceResDto = balanceService.withdraw(userId, changeBalanceDto);

        ResponseDto<BalanceResDto> responseDto = new ResponseDto<>(
                2007,
                "출금 완료",
                balanceResDto
        );

        return ResponseEntity.ok(responseDto);
    }
}
