package com.shotty.shotty.domain.payment.api;

import com.shotty.shotty.domain.payment.application.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PaymentTest {
    private final PaymentService paymentService;
    @GetMapping("/api/payments")
    public void payment() {
        paymentService.doPayment();
    }
}
