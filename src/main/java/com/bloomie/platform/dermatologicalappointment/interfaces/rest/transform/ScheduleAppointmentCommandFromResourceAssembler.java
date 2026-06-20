package com.bloomie.platform.dermatologicalappointment.interfaces.rest.transform;

import com.bloomie.platform.dermatologicalappointment.domain.model.commands.ScheduleDermatologyAppointmentCommand;
import com.bloomie.platform.dermatologicalappointment.interfaces.rest.resources.ScheduleAppointmentResource;

public final class ScheduleAppointmentCommandFromResourceAssembler {

    private ScheduleAppointmentCommandFromResourceAssembler() {}

    public static ScheduleDermatologyAppointmentCommand toCommandFromResource(ScheduleAppointmentResource resource) {
        return new ScheduleDermatologyAppointmentCommand(
                resource.patientId(),
                resource.dermatologistId(),
                resource.scheduledAt());
    }
}
