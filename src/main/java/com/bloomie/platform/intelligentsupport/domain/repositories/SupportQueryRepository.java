package com.bloomie.platform.intelligentsupport.domain.repositories;

import com.bloomie.platform.intelligentsupport.domain.model.aggregates.SupportQuery;
import com.bloomie.platform.intelligentsupport.domain.model.valueobjects.PatientId;
import com.bloomie.platform.intelligentsupport.domain.model.valueobjects.SupportQueryStatus;

import java.util.List;
import java.util.Optional;

public interface SupportQueryRepository {
    Optional<SupportQuery> findById(Long id);
    Optional<SupportQuery> findByPatientIdAndStatus(PatientId patientId, SupportQueryStatus status);
    List<SupportQuery> findAllByPatientId(PatientId patientId);
    SupportQuery save(SupportQuery supportQuery);
}
