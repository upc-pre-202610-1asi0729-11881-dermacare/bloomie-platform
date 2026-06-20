package com.bloomie.platform.dermatologicalappointment.infrastructure.persistence.jpa.entities;

import com.bloomie.platform.dermatologicalappointment.domain.model.valueobjects.AppointmentDateTime;
import com.bloomie.platform.dermatologicalappointment.domain.model.valueobjects.AppointmentStatus;
import com.bloomie.platform.dermatologicalappointment.domain.model.valueobjects.DermatologistId;
import com.bloomie.platform.dermatologicalappointment.domain.model.valueobjects.PatientId;
import com.bloomie.platform.dermatologicalappointment.infrastructure.persistence.jpa.converters.AppointmentDateTimePersistenceConverter;
import com.bloomie.platform.dermatologicalappointment.infrastructure.persistence.jpa.converters.DermatologistIdPersistenceConverter;
import com.bloomie.platform.dermatologicalappointment.infrastructure.persistence.jpa.converters.PatientIdPersistenceConverter;
import com.bloomie.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "appointments")
public class AppointmentPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Convert(converter = PatientIdPersistenceConverter.class)
    @Column(name = "patient_id", nullable = false)
    private PatientId patientId;

    @Convert(converter = DermatologistIdPersistenceConverter.class)
    @Column(name = "dermatologist_id", nullable = false)
    private DermatologistId dermatologistId;

    @Column(name = "payment_id")
    private Long paymentId;

    @Convert(converter = AppointmentDateTimePersistenceConverter.class)
    @Column(name = "scheduled_at", nullable = false)
    private AppointmentDateTime scheduledAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private AppointmentStatus status;

    @Column(name = "cancellation_reason", columnDefinition = "TEXT")
    private String cancellationReason;

    @Column(name = "pending_reprogram_date")
    private String pendingReprogramDate;

    public AppointmentPersistenceEntity() {}

    public PatientId getPatientId() { return patientId; }
    public void setPatientId(PatientId patientId) { this.patientId = patientId; }

    public DermatologistId getDermatologistId() { return dermatologistId; }
    public void setDermatologistId(DermatologistId dermatologistId) { this.dermatologistId = dermatologistId; }

    public Long getPaymentId() { return paymentId; }
    public void setPaymentId(Long paymentId) { this.paymentId = paymentId; }

    public AppointmentDateTime getScheduledAt() { return scheduledAt; }
    public void setScheduledAt(AppointmentDateTime scheduledAt) { this.scheduledAt = scheduledAt; }

    public AppointmentStatus getStatus() { return status; }
    public void setStatus(AppointmentStatus status) { this.status = status; }

    public String getCancellationReason() { return cancellationReason; }
    public void setCancellationReason(String cancellationReason) { this.cancellationReason = cancellationReason; }

    public String getPendingReprogramDate() { return pendingReprogramDate; }
    public void setPendingReprogramDate(String pendingReprogramDate) { this.pendingReprogramDate = pendingReprogramDate; }
}
