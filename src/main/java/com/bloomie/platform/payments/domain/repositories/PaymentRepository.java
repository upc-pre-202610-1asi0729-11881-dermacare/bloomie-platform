package com.bloomie.platform.payments.domain.repositories;

import com.bloomie.platform.payments.domain.model.aggregates.Payment;
import com.bloomie.platform.payments.domain.model.valueobjects.PatientId;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository {
    Optional<Payment> findById(Long id);

    Optional<Payment> findByPatientId(PatientId patientId);

    Payment save(Payment payment);

    boolean existsById(Long id);

    boolean existsByPatientId(PatientId patientId);
}
