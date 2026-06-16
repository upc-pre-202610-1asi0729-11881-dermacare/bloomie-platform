package com.bloomie.platform.dermatologicalAppointment.interfaces.rest.transform;

import com.bloomie.platform.dermatologicalAppointment.domain.model.commands.StartConsultationCommand;
import com.bloomie.platform.dermatologicalAppointment.interfaces.rest.resources.StartConsultationResource;

public final class StartConsultationCommandFromResourceAssembler {

    private StartConsultationCommandFromResourceAssembler() {}

    public static StartConsultationCommand toCommandFromResource(StartConsultationResource resource) {
        return new StartConsultationCommand(resource.appointmentId(), resource.dermatologistId(), resource.patientId());
    }
}
