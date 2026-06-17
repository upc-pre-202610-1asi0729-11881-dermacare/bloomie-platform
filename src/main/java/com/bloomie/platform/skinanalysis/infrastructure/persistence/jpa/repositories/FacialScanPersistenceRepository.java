package com.bloomie.platform.skinanalysis.infrastructure.persistence.jpa.repositories;

import com.bloomie.platform.skinanalysis.domain.model.valueobjects.PatientId;
import com.bloomie.platform.skinanalysis.infrastructure.persistence.jpa.entities.FacialScanPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FacialScanPersistenceRepository
        extends JpaRepository<FacialScanPersistenceEntity, Long> {

    List<FacialScanPersistenceEntity> findAllByPatientId(PatientId patientId);
}
