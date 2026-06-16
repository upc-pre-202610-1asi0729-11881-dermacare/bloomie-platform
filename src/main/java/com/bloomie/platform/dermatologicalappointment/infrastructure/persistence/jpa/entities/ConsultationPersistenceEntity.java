package com.bloomie.platform.dermatologicalappointment.infrastructure.persistence.jpa.entities;

import com.bloomie.platform.dermatologicalappointment.domain.model.valueobjects.ClinicalNotes;
import com.bloomie.platform.dermatologicalappointment.domain.model.valueobjects.ConsultationStatus;
import com.bloomie.platform.dermatologicalappointment.domain.model.valueobjects.DermatologistId;
import com.bloomie.platform.dermatologicalappointment.domain.model.valueobjects.PatientId;
import com.bloomie.platform.dermatologicalappointment.infrastructure.persistence.jpa.converters.ClinicalNotesPersistenceConverter;
import com.bloomie.platform.dermatologicalappointment.infrastructure.persistence.jpa.converters.DermatologistIdPersistenceConverter;
import com.bloomie.platform.dermatologicalappointment.infrastructure.persistence.jpa.converters.PatientIdPersistenceConverter;
import com.bloomie.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "consultations")
public class ConsultationPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "appointment_id", nullable = false)
    private Long appointmentId;

    @Convert(converter = PatientIdPersistenceConverter.class)
    @Column(name = "patient_id", nullable = false)
    private PatientId patientId;

    @Convert(converter = DermatologistIdPersistenceConverter.class)
    @Column(name = "dermatologist_id", nullable = false)
    private DermatologistId dermatologistId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ConsultationStatus status;

    @Convert(converter = ClinicalNotesPersistenceConverter.class)
    @Column(name = "notes", columnDefinition = "TEXT")
    private ClinicalNotes notes;

    @Convert(converter = ClinicalNotesPersistenceConverter.class)
    @Column(name = "recommendations", columnDefinition = "TEXT")
    private ClinicalNotes recommendations;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "consultation_clinical_photo_urls",
            joinColumns = @JoinColumn(name = "consultation_id"))
    @Column(name = "photo_url", nullable = false)
    private List<String> clinicalPhotoUrls = new ArrayList<>();

    @Column(name = "started_at")
    private String startedAt;

    @Column(name = "finished_at")
    private String finishedAt;

    public ConsultationPersistenceEntity() {}

    public Long getAppointmentId() { return appointmentId; }
    public void setAppointmentId(Long appointmentId) { this.appointmentId = appointmentId; }

    public PatientId getPatientId() { return patientId; }
    public void setPatientId(PatientId patientId) { this.patientId = patientId; }

    public DermatologistId getDermatologistId() { return dermatologistId; }
    public void setDermatologistId(DermatologistId dermatologistId) { this.dermatologistId = dermatologistId; }

    public ConsultationStatus getStatus() { return status; }
    public void setStatus(ConsultationStatus status) { this.status = status; }

    public ClinicalNotes getNotes() { return notes; }
    public void setNotes(ClinicalNotes notes) { this.notes = notes; }

    public ClinicalNotes getRecommendations() { return recommendations; }
    public void setRecommendations(ClinicalNotes recommendations) { this.recommendations = recommendations; }

    public List<String> getClinicalPhotoUrls() { return clinicalPhotoUrls; }
    public void setClinicalPhotoUrls(List<String> clinicalPhotoUrls) { this.clinicalPhotoUrls = clinicalPhotoUrls; }

    public String getStartedAt() { return startedAt; }
    public void setStartedAt(String startedAt) { this.startedAt = startedAt; }

    public String getFinishedAt() { return finishedAt; }
    public void setFinishedAt(String finishedAt) { this.finishedAt = finishedAt; }
}
