package com.bloomie.platform.dermatologicalappointment.interfaces.rest.transform;

import com.bloomie.platform.dermatologicalappointment.domain.model.commands.RequestReprogramAppointmentCommand;
import com.bloomie.platform.dermatologicalappointment.interfaces.rest.resources.ReprogramRequestResource;

public final class ReprogramRequestCommandFromResourceAssembler {

    private ReprogramRequestCommandFromResourceAssembler() {}

    public static RequestReprogramAppointmentCommand toCommandFromResource(Long appointmentId,
                                                                            ReprogramRequestResource resource) {
        return new RequestReprogramAppointmentCommand(appointmentId, resource.patientId(), resource.newDate());
    }
}
