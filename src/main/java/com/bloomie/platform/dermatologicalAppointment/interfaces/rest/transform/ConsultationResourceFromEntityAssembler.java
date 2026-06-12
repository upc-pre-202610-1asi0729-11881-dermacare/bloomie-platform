package com.bloomie.platform.dermatologicalAppointment.interfaces.rest.transform;

import com.bloomie.platform.dermatologicalAppointment.domain.model.aggregates.Consultation;
import com.bloomie.platform.dermatologicalAppointment.interfaces.rest.resources.ConsultationResource;

public final class ConsultationResourceFromEntityAssembler {

    private ConsultationResourceFromEntityAssembler() {}

    public static ConsultationResource toResourceFromEntity(Consultation consultation) {
        return new ConsultationResource(
                consultation.getId(),
                consultation.getAppointmentId(),
                consultation.getPatientId().patientId(),
                consultation.getDermatologistId().dermatologistId(),
                consultation.getStatus().name(),
                consultation.getNotes().value(),
                consultation.getRecommendations().value(),
                consultation.getClinicalPhotoUrls(),
                consultation.getStartedAt(),
                consultation.getFinishedAt());
    }
}
