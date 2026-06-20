package com.bloomie.platform.skinanalysis.infrastructure.persistence.jpa.entities;

import com.bloomie.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import com.bloomie.platform.skinanalysis.domain.model.valueobjects.FacialScanId;
import com.bloomie.platform.skinanalysis.domain.model.valueobjects.PatientId;
import com.bloomie.platform.skinanalysis.domain.model.valueobjects.SkinAnalysisStatus;
import com.bloomie.platform.skinanalysis.infrastructure.persistence.jpa.converters.FacialScanIdPersistenceConverter;
import com.bloomie.platform.skinanalysis.infrastructure.persistence.jpa.converters.PatientIdPersistenceConverter;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "skin_analyses")
public class SkinAnalysisPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Convert(converter = PatientIdPersistenceConverter.class)
    @Column(name = "patient_id", nullable = false)
    private PatientId patientId;

    @Convert(converter = FacialScanIdPersistenceConverter.class)
    @Column(name = "facial_scan_id", nullable = false, unique = true)
    private FacialScanId facialScanId;

    @Column(name = "skin_type", nullable = false)
    private String skinType;

    @Column(name = "overall_score", nullable = false)
    private Double overallScore;

    @Column(name = "hydration_score", nullable = false)
    private Double hydrationScore;

    @Column(name = "texture_score", nullable = false)
    private Double textureScore;

    @Column(name = "sensitivity_score", nullable = false)
    private Double sensitivityScore;

    @Column(name = "brightness_score", nullable = false)
    private Double brightnessScore;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private SkinAnalysisStatus status;

    @Column(name = "analyzed_at", nullable = false)
    private LocalDateTime analyzedAt;

    public SkinAnalysisPersistenceEntity() {}

    public PatientId getPatientId() { return patientId; }
    public void setPatientId(PatientId patientId) { this.patientId = patientId; }

    public FacialScanId getFacialScanId() { return facialScanId; }
    public void setFacialScanId(FacialScanId facialScanId) { this.facialScanId = facialScanId; }

    public String getSkinType() { return skinType; }
    public void setSkinType(String skinType) { this.skinType = skinType; }

    public Double getOverallScore() { return overallScore; }
    public void setOverallScore(Double overallScore) { this.overallScore = overallScore; }

    public Double getHydrationScore() { return hydrationScore; }
    public void setHydrationScore(Double hydrationScore) { this.hydrationScore = hydrationScore; }

    public Double getTextureScore() { return textureScore; }
    public void setTextureScore(Double textureScore) { this.textureScore = textureScore; }

    public Double getSensitivityScore() { return sensitivityScore; }
    public void setSensitivityScore(Double sensitivityScore) { this.sensitivityScore = sensitivityScore; }

    public Double getBrightnessScore() { return brightnessScore; }
    public void setBrightnessScore(Double brightnessScore) { this.brightnessScore = brightnessScore; }

    public SkinAnalysisStatus getStatus() { return status; }
    public void setStatus(SkinAnalysisStatus status) { this.status = status; }

    public LocalDateTime getAnalyzedAt() { return analyzedAt; }
    public void setAnalyzedAt(LocalDateTime analyzedAt) { this.analyzedAt = analyzedAt; }
}
