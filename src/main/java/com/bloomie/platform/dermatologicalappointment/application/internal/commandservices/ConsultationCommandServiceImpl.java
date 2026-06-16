package com.bloomie.platform.dermatologicalAppointment.application.internal.commandservices;

import com.bloomie.platform.dermatologicalAppointment.application.commandservices.ConsultationCommandService;
import com.bloomie.platform.dermatologicalAppointment.domain.model.aggregates.Consultation;
import com.bloomie.platform.dermatologicalAppointment.domain.model.commands.FinishConsultationCommand;
import com.bloomie.platform.dermatologicalAppointment.domain.model.commands.RecordDermatologicalDiagnosisCommand;
import com.bloomie.platform.dermatologicalAppointment.domain.model.commands.SaveNotesCommand;
import com.bloomie.platform.dermatologicalAppointment.domain.model.commands.StartConsultationCommand;
import com.bloomie.platform.dermatologicalAppointment.domain.model.commands.UploadClinicalPhotoCommand;
import com.bloomie.platform.dermatologicalAppointment.domain.model.valueobjects.AppointmentStatus;
import com.bloomie.platform.dermatologicalAppointment.domain.repositories.AppointmentRepository;
import com.bloomie.platform.dermatologicalAppointment.domain.repositories.ConsultationRepository;
import com.bloomie.platform.shared.application.result.ApplicationError;
import com.bloomie.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

/**
 * Handles all write operations on the {@link Consultation} aggregate.
 *
 * <p>For <em>new</em> consultations the repository adapter detects {@code id == null}, sets
 * the generated id, calls {@code onStarted()}, and publishes the event. For <em>existing</em>
 * consultations this service calls {@code onXxx()} before saving.</p>
 */
@Service
public class ConsultationCommandServiceImpl implements ConsultationCommandService {

    private static final String APPOINTMENT_NOT_FOUND = "appointment.not.found";
    private static final String APPOINTMENT_NOT_CONFIRMED = "appointment.not.confirmed";
    private static final String CONSULTATION_NOT_FOUND = "consultation.not.found";
    private static final String CONSULTATION_ALREADY_EXISTS = "consultation.already.exists";

    private final ConsultationRepository consultationRepository;
    private final AppointmentRepository appointmentRepository;

    public ConsultationCommandServiceImpl(ConsultationRepository consultationRepository,
                                          AppointmentRepository appointmentRepository) {
        this.consultationRepository = consultationRepository;
        this.appointmentRepository = appointmentRepository;
    }

    /**
     * Creates a new consultation in {@code PENDING} status, immediately starts it
     * ({@code IN_PROGRESS}), and saves. The adapter calls {@code onStarted()} after
     * setting the generated id.
     */
    @Override
    public Result<Consultation, ApplicationError> handle(StartConsultationCommand command) {
        var appointment = appointmentRepository.findById(command.appointmentId());
        if (appointment.isEmpty()) {
            return Result.failure(ApplicationError.notFound("appointment", APPOINTMENT_NOT_FOUND));
        }
        if (appointment.get().getStatus() != AppointmentStatus.CONFIRMED) {
            return Result.failure(ApplicationError.businessRuleViolation("appointment", APPOINTMENT_NOT_CONFIRMED));
        }
        if (consultationRepository.findByAppointmentId(command.appointmentId()).isPresent()) {
            return Result.failure(ApplicationError.conflict("consultation", CONSULTATION_ALREADY_EXISTS));
        }
        var consultation = new Consultation(command);
        consultation.start();
        return Result.success(consultationRepository.save(consultation));
    }

    @Override
    public Result<Consultation, ApplicationError> handle(SaveNotesCommand command) {
        var consultation = consultationRepository.findById(command.consultationId());
        if (consultation.isEmpty()) {
            return Result.failure(ApplicationError.notFound("consultation", CONSULTATION_NOT_FOUND));
        }
        consultation.get().saveNotes(command);
        consultation.get().onNotesSaved();
        return Result.success(consultationRepository.save(consultation.get()));
    }

    @Override
    public Result<Consultation, ApplicationError> handle(RecordDermatologicalDiagnosisCommand command) {
        var consultation = consultationRepository.findById(command.consultationId());
        if (consultation.isEmpty()) {
            return Result.failure(ApplicationError.notFound("consultation", CONSULTATION_NOT_FOUND));
        }
        consultation.get().recordDiagnosis(command);
        consultation.get().onDiagnosisRecorded();
        return Result.success(consultationRepository.save(consultation.get()));
    }

    @Override
    public Result<Consultation, ApplicationError> handle(UploadClinicalPhotoCommand command) {
        var consultation = consultationRepository.findById(command.consultationId());
        if (consultation.isEmpty()) {
            return Result.failure(ApplicationError.notFound("consultation", CONSULTATION_NOT_FOUND));
        }
        consultation.get().uploadPhoto(command);
        consultation.get().onPhotoUploaded(command.photoUrl());
        return Result.success(consultationRepository.save(consultation.get()));
    }

    @Override
    public Result<Consultation, ApplicationError> handle(FinishConsultationCommand command) {
        var consultation = consultationRepository.findById(command.consultationId());
        if (consultation.isEmpty()) {
            return Result.failure(ApplicationError.notFound("consultation", CONSULTATION_NOT_FOUND));
        }
        consultation.get().finish(command);
        consultation.get().onFinished();
        return Result.success(consultationRepository.save(consultation.get()));
    }
}
