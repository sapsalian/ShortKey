package com.shotty.shotty.domain.payment.domain;

import com.shotty.shotty.domain.bid.domain.Bid;
import com.shotty.shotty.domain.payment.dao.PaymentRepository;
import jakarta.persistence.*;
import lombok.Getter;
import org.joda.time.DateTime;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Entity
public class Payment {
    @Id
    @GeneratedValue
    private Long id;

    private int lastViewCount;

    private float amount;

    private float totalAmount;

    private LocalDateTime lastPaymentDate;

    private boolean paid;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bid_id")
    private Bid bid;

    public static Payment create(Bid bid) {
        Payment payment = new Payment();

        payment.lastViewCount = 0;
        payment.amount = 0;
        payment.totalAmount = 0;
        payment.lastPaymentDate = null;
        payment.paid = false;
        payment.bid = bid;

        return payment;
    }


    public void paidUpdate(int lastViewCount, float amount, LocalDateTime lastPaymentDate, boolean paid) {
        this.lastViewCount = lastViewCount;
        this.amount = amount;
        this.totalAmount += amount;
        this.lastPaymentDate = lastPaymentDate;
        this.paid = paid;
    }

    public void unPaidUpdate(int amount, boolean paid) {
        this.amount = amount;
        this.paid = paid;
    }

}
