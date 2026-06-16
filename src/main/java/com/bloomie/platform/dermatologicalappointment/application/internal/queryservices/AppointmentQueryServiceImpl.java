package com.bloomie.platform.dermatologicalAppointment.application.internal.queryservices;

import com.bloomie.platform.dermatologicalAppointment.application.queryservices.AppointmentQueryService;
import com.bloomie.platform.dermatologicalAppointment.domain.model.aggregates.Appointment;
import com.bloomie.platform.dermatologicalAppointment.domain.model.queries.GetAppointmentByIdQuery;
import com.bloomie.platform.dermatologicalAppointment.domain.model.queries.GetAppointmentsByDermatologistIdQuery;
import com.bloomie.platform.dermatologicalAppointment.domain.model.queries.GetAppointmentsByPatientIdQuery;
import com.bloomie.platform.dermatologicalAppointment.domain.model.valueobjects.DermatologistId;
import com.bloomie.platform.dermatologicalAppointment.domain.model.valueobjects.PatientId;
import com.bloomie.platform.dermatologicalAppointment.domain.repositories.AppointmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Application service that handles all read operations on the {@link Appointment} aggregate.
 *
 * <p>Delegates directly to the {@link AppointmentRepository} domain port without any
 * mutation or event publication.</p>
 */
@Service
public class AppointmentQueryServiceImpl implements AppointmentQueryService {

    private final AppointmentRepository appointmentRepository;

    public AppointmentQueryServiceImpl(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public Optional<Appointment> handle(GetAppointmentByIdQuery query) {
        return appointmentRepository.findById(query.appointmentId());
    }

    @Override
    public List<Appointment> handle(GetAppointmentsByPatientIdQuery query) {
        return appointmentRepository.findByPatientId(new PatientId(query.patientId()));
    }

    @Override
    public List<Appointment> handle(GetAppointmentsByDermatologistIdQuery query) {
        return appointmentRepository.findByDermatologistId(new DermatologistId(query.dermatologistId()));
    }
}
