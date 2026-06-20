package com.bloomie.platform.dermatologicalappointment.infrastructure.persistence.jpa.assemblers;

import com.bloomie.platform.dermatologicalappointment.domain.model.aggregates.Consultation;
import com.bloomie.platform.dermatologicalappointment.infrastructure.persistence.jpa.entities.ConsultationPersistenceEntity;

/**
 * Static utility that converts between {@link Consultation} domain aggregates and
 * {@link ConsultationPersistenceEntity} JPA entities.
 */
public final class ConsultationPersistenceAssembler {

    private ConsultationPersistenceAssembler() {}

    public static Consultation toDomainFromPersistence(ConsultationPersistenceEntity entity) {
        return new Consultation(
                entity.getId(),
                entity.getAppointmentId(),
                entity.getPatientId(),
                entity.getDermatologistId(),
                entity.getStatus(),
                entity.getNotes(),
                entity.getRecommendations(),
                entity.getClinicalPhotoUrls(),
                entity.getStartedAt(),
                entity.getFinishedAt());
    }

    public static ConsultationPersistenceEntity toPersistenceFromDomain(Consultation consultation) {
        var entity = new ConsultationPersistenceEntity();
        if (consultation.getId() != null) {
            entity.setId(consultation.getId());
        }
        entity.setAppointmentId(consultation.getAppointmentId());
        entity.setPatientId(consultation.getPatientId());
        entity.setDermatologistId(consultation.getDermatologistId());
        entity.setStatus(consultation.getStatus());
        entity.setNotes(consultation.getNotes());
        entity.setRecommendations(consultation.getRecommendations());
        entity.setClinicalPhotoUrls(consultation.getClinicalPhotoUrls());
        entity.setStartedAt(consultation.getStartedAt());
        entity.setFinishedAt(consultation.getFinishedAt());
        return entity;
    }
}
