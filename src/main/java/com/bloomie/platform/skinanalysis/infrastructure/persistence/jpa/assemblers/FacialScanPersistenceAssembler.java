package com.bloomie.platform.skinanalysis.infrastructure.persistence.jpa.assemblers;

import com.bloomie.platform.skinanalysis.domain.model.aggregates.FacialScan;
import com.bloomie.platform.skinanalysis.infrastructure.persistence.jpa.entities.FacialScanPersistenceEntity;

/**
 * Stateless assembler that converts between the {@link FacialScan} domain aggregate
 * and its JPA counterpart {@link FacialScanPersistenceEntity}.
 */
public final class FacialScanPersistenceAssembler {

    private FacialScanPersistenceAssembler() {}

    /** Reconstructs a {@link FacialScan} aggregate from a stored entity. */
    public static FacialScan toDomainFromPersistence(FacialScanPersistenceEntity entity) {
        return new FacialScan(
                entity.getId(),
                entity.getPatientId(),
                entity.getStatus(),
                entity.getPhotoUrl(),
                entity.getScannedAt());
    }

    /** Converts a {@link FacialScan} aggregate to a persistence entity ready to save. */
    public static FacialScanPersistenceEntity toPersistenceFromDomain(FacialScan facialScan) {
        var entity = new FacialScanPersistenceEntity();
        entity.setId(facialScan.getId());
        entity.setPatientId(facialScan.getPatientIdValue());
        entity.setStatus(facialScan.getStatus());
        entity.setPhotoUrl(facialScan.getPhotoUrl());
        entity.setScannedAt(facialScan.getScannedAt());
        return entity;
    }
}
