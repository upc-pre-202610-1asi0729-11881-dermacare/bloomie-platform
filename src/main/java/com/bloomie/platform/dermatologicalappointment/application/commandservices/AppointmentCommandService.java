package com.bloomie.platform.dermatologicalappointment.application.commandservices;

import com.bloomie.platform.dermatologicalappointment.domain.model.aggregates.Appointment;
import com.bloomie.platform.dermatologicalappointment.domain.model.commands.CancelAppointmentCommand;
import com.bloomie.platform.dermatologicalappointment.domain.model.commands.CompleteAppointmentCommand;
import com.bloomie.platform.dermatologicalappointment.domain.model.commands.ConfirmAppointmentCommand;
import com.bloomie.platform.dermatologicalappointment.domain.model.commands.MarkAppointmentInProgressCommand;
import com.bloomie.platform.dermatologicalappointment.domain.model.commands.ReprogramAppointmentCommand;
import com.bloomie.platform.dermatologicalappointment.domain.model.commands.RequestReprogramAppointmentCommand;
import com.bloomie.platform.dermatologicalappointment.domain.model.commands.ScheduleDermatologyAppointmentCommand;
import com.bloomie.platform.shared.application.result.ApplicationError;
import com.bloomie.platform.shared.application.result.Result;

/**
 * Application service interface for all write operations on the {@link Appointment} aggregate.
 */
public interface AppointmentCommandService {

    Result<Appointment, ApplicationError> handle(ScheduleDermatologyAppointmentCommand command);

    Result<Void, ApplicationError> handle(CancelAppointmentCommand command);

    Result<Void, ApplicationError> handle(RequestReprogramAppointmentCommand command);

    Result<Appointment, ApplicationError> handle(ReprogramAppointmentCommand command);

    Result<Appointment, ApplicationError> handle(ConfirmAppointmentCommand command);

    /** Transitions appointment from {@code CONFIRMED} to {@code IN_PROGRESS} when consultation starts. */
    Result<Appointment, ApplicationError> handle(MarkAppointmentInProgressCommand command);

    /** Transitions appointment to {@code COMPLETED} after its consultation finishes. */
    Result<Appointment, ApplicationError> handle(CompleteAppointmentCommand command);
}
