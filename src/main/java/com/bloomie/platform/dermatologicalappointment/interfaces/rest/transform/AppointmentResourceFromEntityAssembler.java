package com.bloomie.platform.dermatologicalappointment.interfaces.rest.transform;

import com.bloomie.platform.dermatologicalappointment.domain.model.aggregates.Appointment;
import com.bloomie.platform.dermatologicalappointment.interfaces.rest.resources.AppointmentResource;

public final class AppointmentResourceFromEntityAssembler {

    private AppointmentResourceFromEntityAssembler() {}

    public static AppointmentResource toResourceFromEntity(Appointment appointment) {
        return new AppointmentResource(
                appointment.getId(),
                appointment.getPatientId().patientId(),
                appointment.getDermatologistId().dermatologistId(),
                appointment.getScheduledAt().value(),
                appointment.getStatus().name(),
                appointment.getCancellationReason());
    }
}
