package com.bloomie.platform.dermatologicalappointment.interfaces.rest.transform;

import com.bloomie.platform.dermatologicalappointment.domain.model.commands.StartConsultationCommand;
import com.bloomie.platform.dermatologicalappointment.interfaces.rest.resources.StartConsultationResource;

public final class StartConsultationCommandFromResourceAssembler {

    private StartConsultationCommandFromResourceAssembler() {}

    public static StartConsultationCommand toCommandFromResource(StartConsultationResource resource) {
        return new StartConsultationCommand(resource.appointmentId(), resource.dermatologistId(), resource.patientId());
    }
}
