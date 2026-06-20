package com.bloomie.platform.skinanalysis.infrastructure.persistence.jpa.repositories;

import com.bloomie.platform.skinanalysis.domain.model.valueobjects.PatientId;
import com.bloomie.platform.skinanalysis.infrastructure.persistence.jpa.entities.SkinProfilePersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SkinProfilePersistenceRepository
        extends JpaRepository<SkinProfilePersistenceEntity, Long> {

    Optional<SkinProfilePersistenceEntity> findByPatientId(PatientId patientId);

    boolean existsByPatientId(PatientId patientId);
}
