package com.bloomie.platform.skinanalysis.infrastructure.persistence.jpa.entities;

import com.bloomie.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import com.bloomie.platform.skinanalysis.domain.model.valueobjects.FacialScanStatus;
import com.bloomie.platform.skinanalysis.domain.model.valueobjects.PatientId;
import com.bloomie.platform.skinanalysis.infrastructure.persistence.jpa.converters.PatientIdPersistenceConverter;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "facial_scans")
public class FacialScanPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Convert(converter = PatientIdPersistenceConverter.class)
    @Column(name = "patient_id", nullable = false)
    private PatientId patientId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private FacialScanStatus status;

    @Column(name = "photo_url", columnDefinition = "LONGTEXT")
    private String photoUrl;

    @Column(name = "scanned_at", nullable = false)
    private LocalDateTime scannedAt;

    public FacialScanPersistenceEntity() {}

    public PatientId getPatientId() { return patientId; }
    public void setPatientId(PatientId patientId) { this.patientId = patientId; }

    public FacialScanStatus getStatus() { return status; }
    public void setStatus(FacialScanStatus status) { this.status = status; }

    public String getPhotoUrl() { return photoUrl; }
    public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }

    public LocalDateTime getScannedAt() { return scannedAt; }
    public void setScannedAt(LocalDateTime scannedAt) { this.scannedAt = scannedAt; }
}
