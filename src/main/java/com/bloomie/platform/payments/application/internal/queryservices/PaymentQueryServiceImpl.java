package com.bloomie.platform.payments.application.internal.queryservices;

import com.bloomie.platform.payments.application.queryservices.PaymentQueryService;
import com.bloomie.platform.payments.domain.model.aggregates.Payment;
import com.bloomie.platform.payments.domain.model.queries.GetPaymentByAppointmentIdQuery;
import com.bloomie.platform.payments.domain.model.queries.GetPaymentByIdQuery;
import com.bloomie.platform.payments.domain.model.queries.GetPaymentBySubscriptionIdQuery;
import com.bloomie.platform.payments.domain.model.queries.GetPaymentsByPatientIdQuery;
import com.bloomie.platform.payments.domain.repositories.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Application service that resolves Payments bounded-context read queries.
 */
@Service
public class PaymentQueryServiceImpl implements PaymentQueryService {
    private final PaymentRepository paymentRepository;

    /**
     * Creates the query service with the payment repository dependency.
     *
     * @param paymentRepository payment repository port
     */
    public PaymentQueryServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    // inherited javadoc
    @Override
    public Optional<Payment> handle(GetPaymentByIdQuery query) {
        return paymentRepository.findById(query.paymentId());
    }

    // inherited javadoc
    @Override
    public List<Payment> handle(GetPaymentsByPatientIdQuery query) {
        return paymentRepository.findAllByPatientId(query.patientId());
    }

    // inherited javadoc
    @Override
    public Optional<Payment> handle(GetPaymentBySubscriptionIdQuery query) {
        return paymentRepository.findBySubscriptionId(query.subscriptionId());
    }

    // inherited javadoc
    @Override
    public Optional<Payment> handle(GetPaymentByAppointmentIdQuery query) {
        return paymentRepository.findByAppointmentId(query.appointmentId());
    }
}
