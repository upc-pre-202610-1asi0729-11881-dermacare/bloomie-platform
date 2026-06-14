package com.bloomie.platform.payments.domain.repositories;

import com.bloomie.platform.payments.domain.model.aggregates.Payment;
import com.bloomie.platform.payments.domain.model.valueobjects.PatientId;
import com.bloomie.platform.payments.domain.model.valueobjects.SubscriptionId;

import java.util.List;
import java.util.Optional;

/**
 * Payment repository port.
 */
public interface PaymentRepository {
    Optional<Payment> findById(Long id);

    List<Payment> findAllByPatientId(PatientId patientId);

    Optional<Payment> findBySubscriptionId(SubscriptionId subscriptionId);

    Payment save(Payment payment);
}
