package com.bloomie.platform.routinemanagement.domain.model.aggregates;

import com.bloomie.platform.routinemanagement.domain.model.commands.MarkRoutineAsCompletedCommand;
import com.bloomie.platform.routinemanagement.domain.model.events.DailyRoutineCompletionRecordedEvent;
import com.bloomie.platform.routinemanagement.domain.model.valueobjects.PatientId;
import com.bloomie.platform.routinemanagement.domain.model.valueobjects.RoutineId;
import com.bloomie.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Aggregate root that records whether a patient completed their skincare routine on a specific day.
 *
 * <p>A new instance is created when the patient marks their routine as completed.
 * Only one tracking entry is allowed per patient per date.</p>
 */
@Getter
public class DailyTracking extends AbstractDomainAggregateRoot<DailyTracking> {

    @Setter
    private Long id;

    @Setter
    private PatientId patientId;

    @Setter
    private RoutineId routineId;

    @Setter
    private LocalDate date;

    @Setter
    private boolean completed;

    @Setter
    private LocalDateTime completedAt;

    /** Reconstitution constructor — used by the persistence assembler. */
    public DailyTracking() {
    }

    /**
     * Creates a new daily tracking record from a command, marking the routine as completed.
     *
     * @param command command carrying the patient id, routine id and completion date
     */
    public DailyTracking(MarkRoutineAsCompletedCommand command) {
        this.patientId = new PatientId(command.patientId());
        this.routineId = new RoutineId(command.routineId());
        this.date = command.date();
        this.completed = true;
        this.completedAt = LocalDateTime.now();
    }

    /**
     * Registers a {@link DailyRoutineCompletionRecordedEvent} after the tracking entry is persisted.
     *
     * <p>Called by the repository adapter once the JPA identity has been assigned.</p>
     */
    public void onCompleted() {
        registerDomainEvent(DailyRoutineCompletionRecordedEvent.from(this));
    }
}
