package com.bloomie.platform.dermatologicalAppointment.interfaces.rest.transform;

import com.bloomie.platform.dermatologicalAppointment.domain.model.commands.FinishConsultationCommand;
import com.bloomie.platform.dermatologicalAppointment.interfaces.rest.resources.FinishConsultationResource;

public final class FinishConsultationCommandFromResourceAssembler {

    private FinishConsultationCommandFromResourceAssembler() {}

    public static FinishConsultationCommand toCommandFromResource(Long consultationId,
                                                                   FinishConsultationResource resource) {
        return new FinishConsultationCommand(consultationId, resource.dermatologistId());
    }
}
