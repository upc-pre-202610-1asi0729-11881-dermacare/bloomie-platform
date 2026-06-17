package com.bloomie.platform.skinAnalysis.infrastructure.persistence.jpa.entities;

import com.bloomie.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import com.bloomie.platform.skinAnalysis.domain.model.valueobjects.PatientId;
import com.bloomie.platform.skinAnalysis.domain.model.valueobjects.SkinProfileStatus;
import com.bloomie.platform.skinAnalysis.domain.model.valueobjects.SkinTone;
import com.bloomie.platform.skinAnalysis.domain.model.valueobjects.SkinType;
import com.bloomie.platform.skinAnalysis.infrastructure.persistence.jpa.converters.PatientIdPersistenceConverter;
import com.bloomie.platform.skinAnalysis.infrastructure.persistence.jpa.converters.SkinConcernsPersistenceConverter;
import jakarta.persistence.*;

import java.util.List;

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
    @Column(name = "skin_tone", nullable = false)
    private SkinTone skinTone;

    @Convert(converter = SkinConcernsPersistenceConverter.class)
    @Column(name = "concerns", nullable = false, columnDefinition = "TEXT")
    private List<String> concerns;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private SkinProfileStatus status;

    public SkinProfilePersistenceEntity() {}

    public PatientId getPatientId() { return patientId; }
    public void setPatientId(PatientId patientId) { this.patientId = patientId; }

    public SkinType getSkinType() { return skinType; }
    public void setSkinType(SkinType skinType) { this.skinType = skinType; }

    public SkinTone getSkinTone() { return skinTone; }
    public void setSkinTone(SkinTone skinTone) { this.skinTone = skinTone; }

    public List<String> getConcerns() { return concerns; }
    public void setConcerns(List<String> concerns) { this.concerns = concerns; }

    public SkinProfileStatus getStatus() { return status; }
    public void setStatus(SkinProfileStatus status) { this.status = status; }
}
