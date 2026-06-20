package com.bloomie.platform.dermatologicalappointment.interfaces.rest.transform;

import com.bloomie.platform.dermatologicalappointment.domain.model.commands.ConfirmAppointmentCommand;
import com.bloomie.platform.dermatologicalappointment.interfaces.rest.resources.ConfirmAppointmentResource;

public final class ConfirmAppointmentCommandFromResourceAssembler {

    private ConfirmAppointmentCommandFromResourceAssembler() {}

    public static ConfirmAppointmentCommand toCommandFromResource(Long appointmentId,
                                                                   ConfirmAppointmentResource resource) {
        return new ConfirmAppointmentCommand(appointmentId, resource.patientId());
    }
}
