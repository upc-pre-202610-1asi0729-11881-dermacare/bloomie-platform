package com.bloomie.platform.dermatologyCare.infrastructure.persistence.jpa.assemblers;

import com.bloomie.platform.dermatologyCare.domain.model.aggregates.Availability;
import com.bloomie.platform.dermatologyCare.domain.model.valueobjects.TimeSlot;
import com.bloomie.platform.dermatologyCare.infrastructure.persistence.jpa.embeddables.TimeSlotPersistenceEmbeddable;
import com.bloomie.platform.dermatologyCare.infrastructure.persistence.jpa.entities.AvailabilityPersistenceEntity;

/**
 * Stateless assembler that converts between the {@link Availability} domain aggregate
 * and its JPA counterpart {@link AvailabilityPersistenceEntity}.
 */
public final class AvailabilityPersistenceAssembler {

    private AvailabilityPersistenceAssembler() {}

    /** Reconstructs an {@link Availability} aggregate from a stored entity. */
    public static Availability toDomainFromPersistence(AvailabilityPersistenceEntity entity) {
        return new Availability(
                entity.getId(),
                entity.getDermatologistId(),
                entity.getDay(),
                toDomainFromPersistence(entity.getTimeSlot()),
                entity.isActive());
    }

    /** Converts an {@link Availability} aggregate to a persistence entity ready to save. */
    public static AvailabilityPersistenceEntity toPersistenceFromDomain(Availability availability) {
        var entity = new AvailabilityPersistenceEntity();
        entity.setId(availability.getId());
        entity.setDermatologistId(availability.getDermatologistIdValue());
        entity.setDay(availability.getDayofWeek());
        entity.setTimeSlot(toPersistenceFromDomain(availability.getTimeSlot()));
        entity.setActive(availability.isActive());
        return entity;
    }

    private static TimeSlot toDomainFromPersistence(TimeSlotPersistenceEmbeddable value) {
        return value == null ? null : new TimeSlot(value.getStartTime(), value.getEndTime());
    }

    private static TimeSlotPersistenceEmbeddable toPersistenceFromDomain(TimeSlot value) {
        return value == null ? null : new TimeSlotPersistenceEmbeddable(value.startTime(), value.endTime());
    }
}
