package com.bloomie.platform.dermatologicalappointment.application.acl;

import com.bloomie.platform.dermatologicalappointment.application.queryservices.AppointmentQueryService;
import com.bloomie.platform.dermatologicalappointment.domain.model.queries.GetAppointmentByIdQuery;
import com.bloomie.platform.dermatologicalappointment.domain.model.queries.GetAppointmentsByPatientIdQuery;
import com.bloomie.platform.dermatologicalappointment.interfaces.acl.DermatologicalAppointmentContextFacade;
import org.springframework.stereotype.Service;

/**
 * Implements the {@link DermatologicalAppointmentContextFacade} by delegating to
 * the internal query service. Only primitives are returned.
 */
@Service
public class DermatologicalAppointmentContextFacadeImpl implements DermatologicalAppointmentContextFacade {

    private final AppointmentQueryService appointmentQueryService;

    public DermatologicalAppointmentContextFacadeImpl(AppointmentQueryService appointmentQueryService) {
        this.appointmentQueryService = appointmentQueryService;
    }

    @Override
    public boolean existsAppointmentByPatientId(Long patientId) {
        return !appointmentQueryService
                .handle(new GetAppointmentsByPatientIdQuery(patientId))
                .isEmpty();
    }

    @Override
    public String fetchAppointmentStatusById(Long appointmentId) {
        return appointmentQueryService
                .handle(new GetAppointmentByIdQuery(appointmentId))
                .map(appointment -> appointment.getStatus().name())
                .orElse("");
    }
}
