package com.bloomie.platform.skinanalysis.infrastructure.persistence.jpa.repositories;

import com.bloomie.platform.skinanalysis.domain.model.valueobjects.FacialScanId;
import com.bloomie.platform.skinanalysis.domain.model.valueobjects.PatientId;
import com.bloomie.platform.skinanalysis.infrastructure.persistence.jpa.entities.SkinAnalysisPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SkinAnalysisPersistenceRepository
        extends JpaRepository<SkinAnalysisPersistenceEntity, Long> {

    Optional<SkinAnalysisPersistenceEntity> findByFacialScanId(FacialScanId facialScanId);

    List<SkinAnalysisPersistenceEntity> findAllByPatientId(PatientId patientId);
}
