package com.bloomie.platform.dermatologicalAppointment.application.commandservices;

import com.bloomie.platform.dermatologicalAppointment.domain.model.aggregates.Consultation;
import com.bloomie.platform.dermatologicalAppointment.domain.model.commands.FinishConsultationCommand;
import com.bloomie.platform.dermatologicalAppointment.domain.model.commands.RecordDermatologicalDiagnosisCommand;
import com.bloomie.platform.dermatologicalAppointment.domain.model.commands.SaveNotesCommand;
import com.bloomie.platform.dermatologicalAppointment.domain.model.commands.StartConsultationCommand;
import com.bloomie.platform.dermatologicalAppointment.domain.model.commands.UploadClinicalPhotoCommand;
import com.bloomie.platform.shared.application.result.ApplicationError;
import com.bloomie.platform.shared.application.result.Result;

/**
 * Application service interface for all write operations on the {@link Consultation} aggregate.
 */
public interface ConsultationCommandService {

    Result<Consultation, ApplicationError> handle(StartConsultationCommand command);

    Result<Consultation, ApplicationError> handle(SaveNotesCommand command);

    Result<Consultation, ApplicationError> handle(RecordDermatologicalDiagnosisCommand command);

    Result<Consultation, ApplicationError> handle(UploadClinicalPhotoCommand command);

    Result<Consultation, ApplicationError> handle(FinishConsultationCommand command);
}
