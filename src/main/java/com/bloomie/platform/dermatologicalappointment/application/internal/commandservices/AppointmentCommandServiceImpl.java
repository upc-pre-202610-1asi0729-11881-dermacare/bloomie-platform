package com.bloomie.platform.dermatologicalappointment.application.internal.commandservices;

import com.bloomie.platform.dermatologicalappointment.application.commandservices.AppointmentCommandService;
import com.bloomie.platform.dermatologicalappointment.application.internal.outboundservices.acl.ExternalDermatologyCareService;
import com.bloomie.platform.dermatologicalappointment.application.internal.outboundservices.acl.ExternalIamService;
import com.bloomie.platform.dermatologicalappointment.domain.model.aggregates.Appointment;
import com.bloomie.platform.dermatologicalappointment.domain.model.commands.CancelAppointmentCommand;
import com.bloomie.platform.dermatologicalappointment.domain.model.commands.CompleteAppointmentCommand;
import com.bloomie.platform.dermatologicalappointment.domain.model.commands.ConfirmAppointmentCommand;
import com.bloomie.platform.dermatologicalappointment.domain.model.commands.MarkAppointmentInProgressCommand;
import com.bloomie.platform.dermatologicalappointment.domain.model.commands.ReprogramAppointmentCommand;
import com.bloomie.platform.dermatologicalappointment.domain.model.commands.RequestReprogramAppointmentCommand;
import com.bloomie.platform.dermatologicalappointment.domain.model.commands.ScheduleDermatologyAppointmentCommand;
import com.bloomie.platform.dermatologicalappointment.domain.model.valueobjects.AppointmentDateTime;
import com.bloomie.platform.dermatologicalappointment.domain.model.valueobjects.DermatologistId;
import com.bloomie.platform.dermatologicalappointment.domain.repositories.AppointmentRepository;
import com.bloomie.platform.shared.application.result.ApplicationError;
import com.bloomie.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

/**
 * Handles all write operations on the {@link Appointment} aggregate.
 *
 * <p>Domain events for <em>new</em> aggregates are published by the repository adapter
 * (detects {@code id == null}, sets id, calls {@code onScheduled()}). For <em>existing</em>
 * aggregates this service calls the appropriate {@code onXxx()} before saving so the adapter
 * can publish and clear the registered events.</p>
 */
@Service
public class AppointmentCommandServiceImpl implements AppointmentCommandService {

    private static final String PATIENT_NOT_FOUND = "appointment.patient.not.found";
    private static final String DERMATOLOGIST_NOT_FOUND = "appointment.dermatologist.not.found";
    private static final String APPOINTMENT_NOT_FOUND = "appointment.not.found";
    private static final String PATIENT_MISMATCH = "appointment.patient.mismatch";
    private static final String SLOT_ALREADY_TAKEN = "appointment.slot.already.taken";

    private final AppointmentRepository appointmentRepository;
    private final ExternalIamService externalIamService;
    private final ExternalDermatologyCareService externalDermatologyCareService;

    public AppointmentCommandServiceImpl(AppointmentRepository appointmentRepository,
                                         ExternalIamService externalIamService,
                                         ExternalDermatologyCareService externalDermatologyCareService) {
        this.appointmentRepository = appointmentRepository;
        this.externalIamService = externalIamService;
        this.externalDermatologyCareService = externalDermatologyCareService;
    }

    @Override
    public Result<Appointment, ApplicationError> handle(ScheduleDermatologyAppointmentCommand command) {
        if (!externalIamService.existsUserById(command.patientId())) {
            return Result.failure(ApplicationError.notFound("appointment", PATIENT_NOT_FOUND));
        }
        if (!externalDermatologyCareService.existsDermatologistProfile(command.dermatologistId())) {
            return Result.failure(ApplicationError.notFound("appointment", DERMATOLOGIST_NOT_FOUND));
        }
        var scheduledAt = new AppointmentDateTime(command.scheduledAt());
        if (appointmentRepository.existsByDermatologistIdAndDate(
                new DermatologistId(command.dermatologistId()), scheduledAt)) {
            return Result.failure(ApplicationError.conflict("appointment", SLOT_ALREADY_TAKEN));
        }
        return Result.success(appointmentRepository.save(new Appointment(command)));
    }

    @Override
    public Result<Void, ApplicationError> handle(CancelAppointmentCommand command) {
        var appointment = appointmentRepository.findById(command.appointmentId());
        if (appointment.isEmpty()) {
            return Result.failure(ApplicationError.notFound("appointment", APPOINTMENT_NOT_FOUND));
        }
        if (!appointment.get().getPatientId().patientId().equals(command.patientId())) {
            return Result.failure(ApplicationError.businessRuleViolation("appointment", PATIENT_MISMATCH));
        }
        appointment.get().cancel(command);
        appointment.get().onCancelled();
        appointmentRepository.save(appointment.get());
        return Result.success(null);
    }

    @Override
    public Result<Void, ApplicationError> handle(RequestReprogramAppointmentCommand command) {
        var appointment = appointmentRepository.findById(command.appointmentId());
        if (appointment.isEmpty()) {
            return Result.failure(ApplicationError.notFound("appointment", APPOINTMENT_NOT_FOUND));
        }
        if (!appointment.get().getPatientId().patientId().equals(command.patientId())) {
            return Result.failure(ApplicationError.businessRuleViolation("appointment", PATIENT_MISMATCH));
        }
        appointment.get().requestReprogram(command);
        appointment.get().onReprogramRequestSubmitted();
        appointmentRepository.save(appointment.get());
        return Result.success(null);
    }

    @Override
    public Result<Appointment, ApplicationError> handle(ReprogramAppointmentCommand command) {
        var appointment = appointmentRepository.findById(command.appointmentId());
        if (appointment.isEmpty()) {
            return Result.failure(ApplicationError.notFound("appointment", APPOINTMENT_NOT_FOUND));
        }
        appointment.get().reprogram(command);
        appointment.get().onReprogrammed();
        return Result.success(appointmentRepository.save(appointment.get()));
    }

    @Override
    public Result<Appointment, ApplicationError> handle(ConfirmAppointmentCommand command) {
        var appointment = appointmentRepository.findById(command.appointmentId());
        if (appointment.isEmpty()) {
            return Result.failure(ApplicationError.notFound("appointment", APPOINTMENT_NOT_FOUND));
        }
        if (!appointment.get().getPatientId().patientId().equals(command.patientId())) {
            return Result.failure(ApplicationError.businessRuleViolation("appointment", PATIENT_MISMATCH));
        }
        appointment.get().confirm();
        appointment.get().onConfirmed();
        return Result.success(appointmentRepository.save(appointment.get()));
    }

    @Override
    public Result<Appointment, ApplicationError> handle(MarkAppointmentInProgressCommand command) {
        var appointment = appointmentRepository.findById(command.appointmentId());
        if (appointment.isEmpty()) {
            return Result.failure(ApplicationError.notFound("appointment", APPOINTMENT_NOT_FOUND));
        }
        appointment.get().markInProgress();
        return Result.success(appointmentRepository.save(appointment.get()));
    }

    @Override
    public Result<Appointment, ApplicationError> handle(CompleteAppointmentCommand command) {
        var appointment = appointmentRepository.findById(command.appointmentId());
        if (appointment.isEmpty()) {
            return Result.failure(ApplicationError.notFound("appointment", APPOINTMENT_NOT_FOUND));
        }
        appointment.get().complete();
        appointment.get().onCompleted();
        return Result.success(appointmentRepository.save(appointment.get()));
    }
}
