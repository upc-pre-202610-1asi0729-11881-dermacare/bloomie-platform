package com.bloomie.platform.dermatologicalAppointment.interfaces.rest.transform;

import com.bloomie.platform.dermatologicalAppointment.domain.model.aggregates.Appointment;
import com.bloomie.platform.dermatologicalAppointment.interfaces.rest.resources.AppointmentResource;

public final class AppointmentResourceFromEntityAssembler {

    private AppointmentResourceFromEntityAssembler() {}

    public static AppointmentResource toResourceFromEntity(Appointment appointment) {
        return new AppointmentResource(
                appointment.getId(),
                appointment.getPatientId().patientId(),
                appointment.getDermatologistId().dermatologistId(),
                appointment.getPaymentId(),
                appointment.getScheduledAt().value(),
                appointment.getStatus().name(),
                appointment.getCancellationReason());
    }
}
