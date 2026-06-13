// SubscriptionPersistenceRepository.java
package com.bloomie.platform.subscription.infrastructure.repositories;

import com.bloomie.platform.subscription.domain.model.valueobjects.PatientId;
import com.bloomie.platform.subscription.infrastructure.entities.SubscriptionPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubscriptionPersistenceRepository
        extends JpaRepository<SubscriptionPersistenceEntity, Long> {

    @Query("select s from SubscriptionPersistenceEntity s where s.patientId = :patientId")
    Optional<SubscriptionPersistenceEntity> findByPatientId(@Param("patientId") PatientId patientId);

    @Query("select (count(s) > 0) from SubscriptionPersistenceEntity s where s.patientId = :patientId")
    boolean existsByPatientId(@Param("patientId") PatientId patientId);
}