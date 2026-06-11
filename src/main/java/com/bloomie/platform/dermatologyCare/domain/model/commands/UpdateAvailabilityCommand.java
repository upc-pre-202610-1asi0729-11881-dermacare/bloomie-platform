package com.bloomie.platform.dermatologyCare.domain.model.commands;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record UpdateAvailabilityCommand(Long availabilityId, DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
}
