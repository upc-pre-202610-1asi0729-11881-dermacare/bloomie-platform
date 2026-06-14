package com.bloomie.platform.payments.application.internal.queryservices;

import com.bloomie.platform.payments.application.queryservices.PaymentQueryService;
import com.bloomie.platform.payments.domain.model.aggregates.Payment;
import com.bloomie.platform.payments.domain.model.queries.GetPaymentByIdQuery;
import com.bloomie.platform.payments.domain.model.queries.GetPaymentBySubscriptionIdQuery;
import com.bloomie.platform.payments.domain.model.queries.GetPaymentsByPatientIdQuery;
import com.bloomie.platform.payments.domain.repositories.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentQueryServiceImpl implements PaymentQueryService {
    private final PaymentRepository paymentRepository;

    public PaymentQueryServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Optional<Payment> handle(GetPaymentByIdQuery query) {
        return paymentRepository.findById(query.paymentId());
    }

    @Override
    public Optional<Payment> handle(GetPaymentsByPatientIdQuery query) {
        return paymentRepository.findById(query.patientId().patientId());
    }

    @Override
    public Optional<Payment> handle(GetPaymentBySubscriptionIdQuery query) {
        return paymentRepository.findById(query.subscriptionId().subscriptionId());
    }
}
