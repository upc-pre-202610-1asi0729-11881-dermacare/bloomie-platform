package com.bloomie.platform.payments.domain.model.valueobjects;

public record PaymentAmount(Double amount) {
    public PaymentAmount {
        if(amount < 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
    }
}
