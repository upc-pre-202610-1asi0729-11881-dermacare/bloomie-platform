package com.bloomie.platform.dermatologycare.interfaces.rest.transform;

import com.bloomie.platform.dermatologycare.domain.model.commands.DefineAvailabilityCommand;
import com.bloomie.platform.dermatologycare.domain.model.valueobjects.DermatologistId;
import com.bloomie.platform.dermatologycare.interfaces.rest.resources.DefineAvailabilityResource;

import java.time.DayOfWeek;
import java.time.LocalTime;

/**
 * Stateless assembler that converts a {@link DefineAvailabilityResource}
 * into a {@link DefineAvailabilityCommand}.
 */
public final class DefineAvailabilityCommandFromResourceAssembler {

    private DefineAvailabilityCommandFromResourceAssembler() {}

    public static DefineAvailabilityCommand toCommandFromResource(DefineAvailabilityResource resource) {
        return new DefineAvailabilityCommand(
                new DermatologistId(resource.dermatologistId()),
                DayOfWeek.valueOf(resource.day().toUpperCase()),
                LocalTime.parse(resource.startTime()),
                LocalTime.parse(resource.endTime()));
    }
}
