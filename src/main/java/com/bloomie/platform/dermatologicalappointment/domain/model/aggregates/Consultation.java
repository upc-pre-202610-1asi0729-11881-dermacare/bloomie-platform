package com.bloomie.platform.dermatologicalappointment.domain.model.aggregates;

import com.bloomie.platform.dermatologicalappointment.domain.model.commands.FinishConsultationCommand;
import com.bloomie.platform.dermatologicalappointment.domain.model.commands.RecordDermatologicalDiagnosisCommand;
import com.bloomie.platform.dermatologicalappointment.domain.model.commands.SaveNotesCommand;
import com.bloomie.platform.dermatologicalappointment.domain.model.commands.StartConsultationCommand;
import com.bloomie.platform.dermatologicalappointment.domain.model.commands.UploadClinicalPhotoCommand;
import com.bloomie.platform.dermatologicalappointment.domain.model.events.ClinicalNotesSavedEvent;
import com.bloomie.platform.dermatologicalappointment.domain.model.events.ClinicalPhotoUploadedEvent;
import com.bloomie.platform.dermatologicalappointment.domain.model.events.ConsultationFinishedEvent;
import com.bloomie.platform.dermatologicalappointment.domain.model.events.ConsultationStartedEvent;
import com.bloomie.platform.dermatologicalappointment.domain.model.events.DermatologicalDiagnosisRecordedEvent;
import com.bloomie.platform.dermatologicalappointment.domain.model.valueobjects.ClinicalNotes;
import com.bloomie.platform.dermatologicalappointment.domain.model.valueobjects.ConsultationStatus;
import com.bloomie.platform.dermatologicalappointment.domain.model.valueobjects.DermatologistId;
import com.bloomie.platform.dermatologicalappointment.domain.model.valueobjects.PatientId;
import com.bloomie.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Aggregate root representing the clinical consultation session associated with a confirmed
 * dermatological appointment.
 *
 * <p>Lifecycle: {@code PENDING} (created) → {@code IN_PROGRESS} (started) → {@code COMPLETED} (finished).
 * {@code appointmentId} is a {@code Long} (not a VO) because {@link Appointment} belongs to this BC.</p>
 *
 * <p>For <em>new</em> consultations the repository adapter detects {@code id == null}, sets the
 * generated id, then calls {@link #onStarted()}. For updates the application service calls the
 * relevant {@code onXxx()} before saving.</p>
 */
public class Consultation extends AbstractDomainAggregateRoot<Consultation> {

    private static final String CANNOT_START_KEY = "consultation.cannot.start";
    private static final String CANNOT_RECORD_DIAGNOSIS_KEY = "consultation.cannot.record.diagnosis";
    private static final String CANNOT_UPLOAD_PHOTO_KEY = "consultation.cannot.upload.photo";
    private static final String CANNOT_SAVE_NOTES_KEY = "consultation.cannot.save.notes";
    private static final String CANNOT_FINISH_KEY = "consultation.cannot.finish";
    private static final String PHOTO_URL_BLANK_KEY = "appointment.photo.url.blank";

    @Getter
    @Setter
    private Long id;

    @Getter
    private Long appointmentId;

    @Getter
    private PatientId patientId;

    @Getter
    private DermatologistId dermatologistId;

    @Getter
    private ConsultationStatus status;

    /** Progressive clinical observations; blank until the dermatologist writes. */
    @Getter
    private ClinicalNotes notes;

    /** Treatment recommendations; blank until recorded. */
    @Getter
    private ClinicalNotes recommendations;

    /** URLs of clinical photos attached during the session. */
    @Getter
    private List<String> clinicalPhotoUrls;

    /** ISO-8601 string set when the session starts; {@code null} before start. */
    @Getter
    private String startedAt;

    /** ISO-8601 string set when the session finishes; {@code null} before finish. */
    @Getter
    private String finishedAt;

    /**
     * Creates a new consultation in {@code PENDING} status. The caller must then invoke
     * {@link #start()} to transition it to {@code IN_PROGRESS} before saving.
     */
    public Consultation(StartConsultationCommand command) {
        this.appointmentId = command.appointmentId();
        this.dermatologistId = new DermatologistId(command.dermatologistId());
        this.patientId = new PatientId(command.patientId());
        this.status = ConsultationStatus.PENDING;
        this.notes = new ClinicalNotes("");
        this.recommendations = new ClinicalNotes("");
        this.clinicalPhotoUrls = new ArrayList<>();
    }

    /** Reconstitution constructor — used by the persistence assembler; skips business validation. */
    public Consultation(Long id, Long appointmentId, PatientId patientId,
                        DermatologistId dermatologistId, ConsultationStatus status,
                        ClinicalNotes notes, ClinicalNotes recommendations,
                        List<String> clinicalPhotoUrls, String startedAt, String finishedAt) {
        this.id = id;
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.dermatologistId = dermatologistId;
        this.status = status;
        this.notes = notes;
        this.recommendations = recommendations;
        this.clinicalPhotoUrls = clinicalPhotoUrls != null ? clinicalPhotoUrls : new ArrayList<>();
        this.startedAt = startedAt;
        this.finishedAt = finishedAt;
    }

    /** Transitions from {@code PENDING} to {@code IN_PROGRESS} and records the start timestamp. */
    public void start() {
        if (this.status != ConsultationStatus.PENDING) {
            throw new IllegalStateException(CANNOT_START_KEY);
        }
        this.status = ConsultationStatus.IN_PROGRESS;
        this.startedAt = LocalDateTime.now().toString();
    }

    /** Called by the repository adapter after persisting a new consultation (id is set first). */
    public void onStarted() {
        registerDomainEvent(ConsultationStartedEvent.from(this));
    }

    /** Saves partial clinical notes mid-session without changing status. */
    public void saveNotes(SaveNotesCommand command) {
        if (this.status != ConsultationStatus.IN_PROGRESS) {
            throw new IllegalStateException(CANNOT_SAVE_NOTES_KEY);
        }
        this.notes = new ClinicalNotes(command.notes());
    }

    /** Registers {@link ClinicalNotesSavedEvent}. Must be called after {@link #saveNotes(SaveNotesCommand)}. */
    public void onNotesSaved() {
        registerDomainEvent(ClinicalNotesSavedEvent.from(this));
    }

    /** Records the final diagnosis notes and recommendations. */
    public void recordDiagnosis(RecordDermatologicalDiagnosisCommand command) {
        if (this.status != ConsultationStatus.IN_PROGRESS) {
            throw new IllegalStateException(CANNOT_RECORD_DIAGNOSIS_KEY);
        }
        this.notes = new ClinicalNotes(command.notes());
        this.recommendations = new ClinicalNotes(command.recommendations());
    }

    /** Registers {@link DermatologicalDiagnosisRecordedEvent}. */
    public void onDiagnosisRecorded() {
        registerDomainEvent(DermatologicalDiagnosisRecordedEvent.from(this));
    }

    /** Adds a clinical photo URL to the session. */
    public void uploadPhoto(UploadClinicalPhotoCommand command) {
        if (this.status != ConsultationStatus.IN_PROGRESS) {
            throw new IllegalStateException(CANNOT_UPLOAD_PHOTO_KEY);
        }
        if (command.photoUrl() == null || command.photoUrl().isBlank()) {
            throw new IllegalArgumentException(PHOTO_URL_BLANK_KEY);
        }
        this.clinicalPhotoUrls.add(command.photoUrl());
    }

    /** Registers {@link ClinicalPhotoUploadedEvent} with the most recently uploaded URL. */
    public void onPhotoUploaded(String uploadedUrl) {
        registerDomainEvent(ClinicalPhotoUploadedEvent.from(this, uploadedUrl));
    }

    /** Transitions from {@code IN_PROGRESS} to {@code COMPLETED} and records the finish timestamp. */
    public void finish(FinishConsultationCommand command) {
        if (this.status != ConsultationStatus.IN_PROGRESS) {
            throw new IllegalStateException(CANNOT_FINISH_KEY);
        }
        this.status = ConsultationStatus.COMPLETED;
        this.finishedAt = LocalDateTime.now().toString();
    }

    /** Registers {@link ConsultationFinishedEvent}. Must be called after {@link #finish(FinishConsultationCommand)}. */
    public void onFinished() {
        registerDomainEvent(ConsultationFinishedEvent.from(this));
    }
}
