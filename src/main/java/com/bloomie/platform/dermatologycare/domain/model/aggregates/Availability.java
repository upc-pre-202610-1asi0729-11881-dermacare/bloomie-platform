package com.bloomie.platform.dermatologycare.domain.model.aggregates;

import com.bloomie.platform.dermatologycare.domain.model.commands.DefineAvailabilityCommand;
import com.bloomie.platform.dermatologycare.domain.model.commands.UpdateAvailabilityCommand;
import com.bloomie.platform.dermatologycare.domain.model.events.AvailabilityDefinedEvent;
import com.bloomie.platform.dermatologycare.domain.model.events.AvailabilityUpdatedEvent;
import com.bloomie.platform.dermatologycare.domain.model.valueobjects.DermatologistId;
import com.bloomie.platform.dermatologycare.domain.model.valueobjects.TimeSlot;
import com.bloomie.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;

/**
 * Aggregate root for a dermatologist's weekly availability slot.
 *
 * <p>One Availability instance represents a recurring time window on a specific
 * day of the week. When first defined, an {@link AvailabilityDefinedEvent} is
 * published so the Dermatological Appointment BC can create appointment slots.</p>
 */
public class Availability extends AbstractDomainAggregateRoot<Availability> {

    @Getter
    @Setter
    private Long id;

    private DermatologistId dermatologistId;
    private DayOfWeek day;
    private TimeSlot timeSlot;
    private boolean active;

    /** Full reconstitution constructor used by the persistence assembler. */
    public Availability(Long id, DermatologistId dermatologistId, DayOfWeek day, TimeSlot timeSlot, boolean active) {
        this.id = id;
        this.dermatologistId = dermatologistId;
        this.day = day;
        this.timeSlot = timeSlot;
        this.active = active;
    }

    /** Creates a new availability from a define command. Active by default. */
    public Availability(DefineAvailabilityCommand command) {
        this.dermatologistId = command.dermatologistId();
        this.day = command.dayOfWeek();
        this.timeSlot = new TimeSlot(command.startTime(), command.endTime());
        this.active = true;
    }

    /** Replaces the day and time slot with the values from the update command. */
    public void update(UpdateAvailabilityCommand command) {
        this.day = command.dayOfWeek();
        this.timeSlot = new TimeSlot(command.startTime(), command.endTime());
    }

    /** Registers an {@link AvailabilityDefinedEvent} after a new availability is persisted. */
    public void onDefined() {
        registerDomainEvent(AvailabilityDefinedEvent.from(this));
    }

    /** Registers an {@link AvailabilityUpdatedEvent} after an availability update is persisted. */
    public void onUpdated() {
        registerDomainEvent(AvailabilityUpdatedEvent.from(this));
    }

    public Long getDermatologistId() { return dermatologistId.dermatologistId(); }
    public DermatologistId getDermatologistIdValue() { return dermatologistId; }
    public DayOfWeek getDayofWeek() { return day; }
    public TimeSlot getTimeSlot() { return timeSlot; }
    public boolean isActive() { return active; }
}
