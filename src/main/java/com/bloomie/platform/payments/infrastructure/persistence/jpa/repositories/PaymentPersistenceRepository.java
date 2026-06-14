package com.bloomie.platform.payments.infrastructure.persistence.jpa.repositories;

import com.bloomie.platform.payments.domain.model.valueobjects.PatientId;
import com.bloomie.platform.payments.domain.model.valueobjects.SubscriptionId;
import com.bloomie.platform.payments.infrastructure.persistence.jpa.entities.PaymentPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for {@link PaymentPersistenceEntity}.
 */
@Repository
public interface PaymentPersistenceRepository extends JpaRepository<PaymentPersistenceEntity, Long> {
    List<PaymentPersistenceEntity> findAllByPatientId(PatientId patientId);
    Optional<PaymentPersistenceEntity> findBySubscriptionId(SubscriptionId subscriptionId);
}
