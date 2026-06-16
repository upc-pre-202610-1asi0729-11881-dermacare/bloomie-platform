package com.bloomie.platform.dermatologyCare.interfaces.rest.transform;

import com.bloomie.platform.dermatologyCare.domain.model.aggregates.Availability;
import com.bloomie.platform.dermatologyCare.interfaces.rest.resources.AvailabilityResource;

/**
 * Stateless assembler that converts an {@link Availability} aggregate
 * into an {@link AvailabilityResource} response DTO.
 */
public final class AvailabilityResourceFromEntityAssembler {

    private AvailabilityResourceFromEntityAssembler() {}

    public static AvailabilityResource toResourceFromEntity(Availability availability) {
        return new AvailabilityResource(
                availability.getId(),
                availability.getDermatologistId(),
                availability.getDayofWeek().name(),
                availability.getTimeSlot().startTime().toString(),
                availability.getTimeSlot().endTime().toString(),
                availability.isActive());
    }
}
