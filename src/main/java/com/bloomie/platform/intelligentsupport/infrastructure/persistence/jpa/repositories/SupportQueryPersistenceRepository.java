package com.bloomie.platform.intelligentsupport.infrastructure.persistence.jpa.repositories;

import com.bloomie.platform.intelligentsupport.domain.model.valueobjects.PatientId;
import com.bloomie.platform.intelligentsupport.domain.model.valueobjects.SupportQueryStatus;
import com.bloomie.platform.intelligentsupport.infrastructure.persistence.jpa.entities.SupportQueryPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SupportQueryPersistenceRepository
        extends JpaRepository<SupportQueryPersistenceEntity, Long> {

    Optional<SupportQueryPersistenceEntity> findByPatientIdAndStatus(
            PatientId patientId, SupportQueryStatus status);

    List<SupportQueryPersistenceEntity> findAllByPatientId(PatientId patientId);
}