package com.bloomie.platform.payments.domain.repositories;

import com.bloomie.platform.payments.domain.model.aggregates.Payment;
import com.bloomie.platform.payments.domain.model.valueobjects.PatientId;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository {
    Optional<Payment> findById(Long id);

    List<Payment> findAllByPatientId(PatientId patientId);

    Payment save(Payment payment);
}
