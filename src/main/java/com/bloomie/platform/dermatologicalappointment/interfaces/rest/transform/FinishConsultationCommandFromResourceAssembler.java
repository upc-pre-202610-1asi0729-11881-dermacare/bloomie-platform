package com.bloomie.platform.dermatologicalappointment.interfaces.rest.transform;

import com.bloomie.platform.dermatologicalappointment.domain.model.commands.FinishConsultationCommand;
import com.bloomie.platform.dermatologicalappointment.interfaces.rest.resources.FinishConsultationResource;

public final class FinishConsultationCommandFromResourceAssembler {

    private FinishConsultationCommandFromResourceAssembler() {}

    public static FinishConsultationCommand toCommandFromResource(Long consultationId,
                                                                   FinishConsultationResource resource) {
        return new FinishConsultationCommand(consultationId, resource.dermatologistId());
    }
}
