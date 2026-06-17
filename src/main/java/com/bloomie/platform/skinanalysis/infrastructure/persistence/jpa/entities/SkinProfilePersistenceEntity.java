package com.bloomie.platform.skinanalysis.infrastructure.persistence.jpa.entities;

import com.bloomie.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import com.bloomie.platform.skinanalysis.domain.model.valueobjects.PatientId;
import com.bloomie.platform.skinanalysis.domain.model.valueobjects.Sensitivity;
import com.bloomie.platform.skinanalysis.domain.model.valueobjects.SkinProfileStatus;
import com.bloomie.platform.skinanalysis.domain.model.valueobjects.SkinType;
import com.bloomie.platform.skinanalysis.infrastructure.persistence.jpa.converters.PatientIdPersistenceConverter;
import jakarta.persistence.*;

@Entity
@Table(name = "skin_profiles")
public class SkinProfilePersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Convert(converter = PatientIdPersistenceConverter.class)
    @Column(name = "patient_id", nullable = false, unique = true)
    private PatientId patientId;

    @Enumerated(EnumType.STRING)
    @Column(name = "skin_type", nullable = false)
    private SkinType skinType;

    @Enumerated(EnumType.STRING)
    @Column(name = "sensitivity", nullable = false)
    private Sensitivity sensitivity;

    @Column(name = "water_intake", nullable = false)
    private String waterIntake;

    @Column(name = "sun_exposure", nullable = false)
    private String sunExposure;

    @Column(name = "sleep_hours", nullable = false)
    private String sleepHours;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private SkinProfileStatus status;

    public SkinProfilePersistenceEntity() {}

    public PatientId getPatientId() { return patientId; }
    public void setPatientId(PatientId patientId) { this.patientId = patientId; }

    public SkinType getSkinType() { return skinType; }
    public void setSkinType(SkinType skinType) { this.skinType = skinType; }

    public Sensitivity getSensitivity() { return sensitivity; }
    public void setSensitivity(Sensitivity sensitivity) { this.sensitivity = sensitivity; }

    public String getWaterIntake() { return waterIntake; }
    public void setWaterIntake(String waterIntake) { this.waterIntake = waterIntake; }

    public String getSunExposure() { return sunExposure; }
    public void setSunExposure(String sunExposure) { this.sunExposure = sunExposure; }

    public String getSleepHours() { return sleepHours; }
    public void setSleepHours(String sleepHours) { this.sleepHours = sleepHours; }

    public SkinProfileStatus getStatus() { return status; }
    public void setStatus(SkinProfileStatus status) { this.status = status; }
}
