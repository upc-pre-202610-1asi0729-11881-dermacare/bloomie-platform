package com.bloomie.platform.dermatologicalappointment.interfaces.rest.transform;

import com.bloomie.platform.dermatologicalappointment.domain.model.commands.CancelAppointmentCommand;
import com.bloomie.platform.dermatologicalappointment.interfaces.rest.resources.CancelAppointmentResource;

public final class CancelAppointmentCommandFromResourceAssembler {

    private CancelAppointmentCommandFromResourceAssembler() {}

    public static CancelAppointmentCommand toCommandFromResource(Long appointmentId,
                                                                  CancelAppointmentResource resource) {
        return new CancelAppointmentCommand(appointmentId, resource.patientId(), resource.cancellationReason());
    }
}
