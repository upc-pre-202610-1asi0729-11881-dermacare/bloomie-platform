package com.bloomie.platform.payments.infrastructure.persistence.jpa.repositories;

import com.bloomie.platform.payments.domain.model.valueobjects.PatientId;
import com.bloomie.platform.payments.domain.model.aggregates.Payment;
import com.bloomie.platform.payments.infrastructure.persistence.jpa.entities.PaymentPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentPersistenceRepository extends JpaRepository<PaymentPersistenceEntity, Long> {
    List<PaymentPersistenceEntity> findAllByPatientId(PatientId patientId);
}
