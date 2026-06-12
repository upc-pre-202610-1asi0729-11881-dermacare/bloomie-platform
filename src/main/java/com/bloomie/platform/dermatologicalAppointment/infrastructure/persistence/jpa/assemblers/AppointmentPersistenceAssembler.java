package com.bloomie.platform.dermatologicalAppointment.infrastructure.persistence.jpa.assemblers;

import com.bloomie.platform.dermatologicalAppointment.domain.model.aggregates.Appointment;
import com.bloomie.platform.dermatologicalAppointment.infrastructure.persistence.jpa.entities.AppointmentPersistenceEntity;

/**
 * Static utility that converts between {@link Appointment} domain aggregates and
 * {@link AppointmentPersistenceEntity} JPA entities.
 */
public final class AppointmentPersistenceAssembler {

    private AppointmentPersistenceAssembler() {}

    public static Appointment toDomainFromPersistence(AppointmentPersistenceEntity entity) {
        return new Appointment(
                entity.getId(),
                entity.getPatientId(),
                entity.getDermatologistId(),
                entity.getPaymentId(),
                entity.getScheduledAt(),
                entity.getStatus(),
                entity.getCancellationReason(),
                entity.getPendingReprogramDate());
    }

    public static AppointmentPersistenceEntity toPersistenceFromDomain(Appointment appointment) {
        var entity = new AppointmentPersistenceEntity();
        if (appointment.getId() != null) {
            entity.setId(appointment.getId());
        }
        entity.setPatientId(appointment.getPatientId());
        entity.setDermatologistId(appointment.getDermatologistId());
        entity.setPaymentId(appointment.getPaymentId());
        entity.setScheduledAt(appointment.getScheduledAt());
        entity.setStatus(appointment.getStatus());
        entity.setCancellationReason(appointment.getCancellationReason());
        entity.setPendingReprogramDate(appointment.getPendingReprogramDate());
        return entity;
    }
}
