package com.bloomie.platform.dermatologicalappointment.domain.model.aggregates;

import com.bloomie.platform.dermatologicalappointment.domain.model.commands.CancelAppointmentCommand;
import com.bloomie.platform.dermatologicalappointment.domain.model.commands.ConfirmAppointmentCommand;
import com.bloomie.platform.dermatologicalappointment.domain.model.commands.ReprogramAppointmentCommand;
import com.bloomie.platform.dermatologicalappointment.domain.model.commands.RequestReprogramAppointmentCommand;
import com.bloomie.platform.dermatologicalappointment.domain.model.commands.ScheduleDermatologyAppointmentCommand;
import com.bloomie.platform.dermatologicalappointment.domain.model.events.AppointmentCancelledEvent;
import com.bloomie.platform.dermatologicalappointment.domain.model.events.AppointmentCompletedEvent;
import com.bloomie.platform.dermatologicalappointment.domain.model.events.AppointmentConfirmedEvent;
import com.bloomie.platform.dermatologicalappointment.domain.model.events.AppointmentReprogramRequestSubmittedEvent;
import com.bloomie.platform.dermatologicalappointment.domain.model.events.AppointmentReprogrammedEvent;
import com.bloomie.platform.dermatologicalappointment.domain.model.events.DermatologyAppointmentScheduledEvent;
import com.bloomie.platform.dermatologicalappointment.domain.model.valueobjects.AppointmentDateTime;
import com.bloomie.platform.dermatologicalappointment.domain.model.valueobjects.AppointmentStatus;
import com.bloomie.platform.dermatologicalappointment.domain.model.valueobjects.DermatologistId;
import com.bloomie.platform.dermatologicalappointment.domain.model.valueobjects.PatientId;
import com.bloomie.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Aggregate root representing a dermatological appointment between a patient and a dermatologist.
 *
 * <p>Lifecycle statuses: {@code SCHEDULED → CONFIRMED → IN_PROGRESS → COMPLETED} (happy path),
 * with cancellation possible from {@code SCHEDULED} or {@code CONFIRMED}.</p>
 *
 * <p>Domain events for <em>new</em> aggregates are published by the repository adapter, which
 * detects {@code id == null} and calls {@link #onScheduled()} after setting the generated id.
 * For <em>existing</em> aggregates the application service calls the relevant {@code onXxx()}
 * before saving so the adapter can publish and clear the registered events.</p>
 */
public class Appointment extends AbstractDomainAggregateRoot<Appointment> {

    private static final String CANNOT_CANCEL_KEY = "appointment.cannot.cancel";
    private static final String CANCELLATION_REASON_REQUIRED_KEY = "appointment.cancellation.reason.required";
    private static final String CANNOT_REPROGRAM_KEY = "appointment.cannot.reprogram";
    private static final String CANNOT_CONFIRM_KEY = "appointment.cannot.confirm";
    private static final String CANNOT_COMPLETE_KEY = "appointment.cannot.complete";
    private static final String CANNOT_MARK_IN_PROGRESS_KEY = "appointment.cannot.mark.in.progress";
    private static final String MUST_BE_FUTURE_KEY = "appointment.scheduled.at.must.be.future";
    private static final long REFUND_WINDOW_HOURS = 24L;

    @Getter
    @Setter
    private Long id;

    @Getter
    private PatientId patientId;

    @Getter
    private DermatologistId dermatologistId;

    /** External reference to the payment record; set asynchronously after payment is processed. */
    @Getter
    @Setter
    private Long paymentId;

    @Getter
    private AppointmentDateTime scheduledAt;

    @Getter
    private AppointmentStatus status;

    /** Reason supplied by the patient at cancellation time; {@code null} before cancellation. */
    @Getter
    private String cancellationReason;

    /**
     * Stores the patient's proposed new date during a reprogram request.
     * Cleared when {@link #reprogram(ReprogramAppointmentCommand)} is called.
     */
    @Getter
    private String pendingReprogramDate;

    /**
     * Creates a new appointment. Validates that {@code scheduledAt} is in the future.
     */
    public Appointment(ScheduleDermatologyAppointmentCommand command) {
        var dateTime = LocalDateTime.parse(command.scheduledAt());
        if (!dateTime.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException(MUST_BE_FUTURE_KEY);
        }
        this.patientId = new PatientId(command.patientId());
        this.dermatologistId = new DermatologistId(command.dermatologistId());
        this.scheduledAt = new AppointmentDateTime(command.scheduledAt());
        this.status = AppointmentStatus.SCHEDULED;
    }

    /** Reconstitution constructor — used by the persistence assembler; skips business validation. */
    public Appointment(Long id, PatientId patientId, DermatologistId dermatologistId,
                       Long paymentId, AppointmentDateTime scheduledAt, AppointmentStatus status,
                       String cancellationReason, String pendingReprogramDate) {
        this.id = id;
        this.patientId = patientId;
        this.dermatologistId = dermatologistId;
        this.paymentId = paymentId;
        this.scheduledAt = scheduledAt;
        this.status = status;
        this.cancellationReason = cancellationReason;
        this.pendingReprogramDate = pendingReprogramDate;
    }

    /** Called by the repository adapter after persisting a new appointment (id is set first). */
    public void onScheduled() {
        registerDomainEvent(DermatologyAppointmentScheduledEvent.from(this));
    }

    /**
     * Cancels the appointment. Requires a non-blank {@code cancellationReason}.
     * Only {@code SCHEDULED} or {@code CONFIRMED} appointments can be cancelled.
     */
    public void cancel(CancelAppointmentCommand command) {
        if (this.status != AppointmentStatus.SCHEDULED && this.status != AppointmentStatus.CONFIRMED) {
            throw new IllegalStateException(CANNOT_CANCEL_KEY);
        }
        if (command.cancellationReason() == null || command.cancellationReason().isBlank()) {
            throw new IllegalArgumentException(CANCELLATION_REASON_REQUIRED_KEY);
        }
        this.cancellationReason = command.cancellationReason();
        this.status = AppointmentStatus.CANCELLED;
    }

    /** Registers {@link AppointmentCancelledEvent}. Must be called after {@link #cancel(CancelAppointmentCommand)}. */
    public void onCancelled() {
        registerDomainEvent(AppointmentCancelledEvent.from(this));
    }

    /**
     * Records the patient's request to move the appointment. Stores the proposed new date
     * in {@code pendingReprogramDate} for the downstream reprogram policy.
     */
    public void requestReprogram(RequestReprogramAppointmentCommand command) {
        if (this.status != AppointmentStatus.SCHEDULED && this.status != AppointmentStatus.CONFIRMED) {
            throw new IllegalStateException(CANNOT_REPROGRAM_KEY);
        }
        this.pendingReprogramDate = command.newDate();
    }

    /** Registers {@link AppointmentReprogramRequestSubmittedEvent}. */
    public void onReprogramRequestSubmitted() {
        registerDomainEvent(AppointmentReprogramRequestSubmittedEvent.from(this));
    }

    /** Applies the new date and transitions status back to {@code SCHEDULED}. */
    public void reprogram(ReprogramAppointmentCommand command) {
        this.scheduledAt = new AppointmentDateTime(command.newDate());
        this.pendingReprogramDate = null;
        this.status = AppointmentStatus.SCHEDULED;
    }

    /** Registers {@link AppointmentReprogrammedEvent}. */
    public void onReprogrammed() {
        registerDomainEvent(AppointmentReprogrammedEvent.from(this));
    }

    /** Transitions from {@code SCHEDULED} to {@code CONFIRMED}. */
    public void confirm() {
        if (this.status != AppointmentStatus.SCHEDULED) {
            throw new IllegalStateException(CANNOT_CONFIRM_KEY);
        }
        this.status = AppointmentStatus.CONFIRMED;
    }

    /** Registers {@link AppointmentConfirmedEvent}. */
    public void onConfirmed() {
        registerDomainEvent(AppointmentConfirmedEvent.from(this));
    }

    /** Transitions from {@code CONFIRMED} to {@code IN_PROGRESS} when a consultation starts. */
    public void markInProgress() {
        if (this.status != AppointmentStatus.CONFIRMED) {
            throw new IllegalStateException(CANNOT_MARK_IN_PROGRESS_KEY);
        }
        this.status = AppointmentStatus.IN_PROGRESS;
    }

    /** Transitions from {@code IN_PROGRESS} to {@code COMPLETED}. */
    public void complete() {
        if (this.status == AppointmentStatus.CANCELLED || this.status == AppointmentStatus.COMPLETED) {
            throw new IllegalStateException(CANNOT_COMPLETE_KEY);
        }
        this.status = AppointmentStatus.COMPLETED;
    }

    /** Registers {@link AppointmentCompletedEvent}. */
    public void onCompleted() {
        registerDomainEvent(AppointmentCompletedEvent.from(this));
    }

    /**
     * Returns {@code true} if the scheduled time is more than 24 hours from now,
     * meaning the patient is eligible for a full refund on cancellation.
     */
    public boolean isEligibleForRefund() {
        var scheduledDateTime = LocalDateTime.parse(scheduledAt.value());
        return scheduledDateTime.isAfter(LocalDateTime.now().plusHours(REFUND_WINDOW_HOURS));
    }
}
