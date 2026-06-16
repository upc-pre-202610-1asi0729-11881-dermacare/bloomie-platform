package com.bloomie.platform.dermatologicalAppointment.interfaces.rest.transform;

import com.bloomie.platform.dermatologicalAppointment.domain.model.commands.ConfirmAppointmentCommand;
import com.bloomie.platform.dermatologicalAppointment.interfaces.rest.resources.ConfirmAppointmentResource;

public final class ConfirmAppointmentCommandFromResourceAssembler {

    private ConfirmAppointmentCommandFromResourceAssembler() {}

    public static ConfirmAppointmentCommand toCommandFromResource(Long appointmentId,
                                                                   ConfirmAppointmentResource resource) {
        return new ConfirmAppointmentCommand(appointmentId, resource.patientId());
    }
}
