package com.bloomie.platform.dermatologycare.interfaces.rest.transform;

import com.bloomie.platform.dermatologycare.domain.model.commands.UpdateAvailabilityCommand;
import com.bloomie.platform.dermatologycare.interfaces.rest.resources.UpdateAvailabilityResource;

import java.time.DayOfWeek;
import java.time.LocalTime;

/**
 * Stateless assembler that converts an {@link UpdateAvailabilityResource}
 * into an {@link UpdateAvailabilityCommand}.
 */
public final class UpdateAvailabilityCommandFromResourceAssembler {

    private UpdateAvailabilityCommandFromResourceAssembler() {}

    public static UpdateAvailabilityCommand toCommandFromResource(Long availabilityId, UpdateAvailabilityResource resource) {
        return new UpdateAvailabilityCommand(
                availabilityId,
                DayOfWeek.valueOf(resource.day().toUpperCase()),
                LocalTime.parse(resource.startTime()),
                LocalTime.parse(resource.endTime()),
                resource.active() == null || resource.active());
    }
}
