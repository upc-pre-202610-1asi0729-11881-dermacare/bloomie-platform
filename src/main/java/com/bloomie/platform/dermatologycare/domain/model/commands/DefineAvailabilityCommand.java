package com.bloomie.platform.dermatologycare.domain.model.commands;

import com.bloomie.platform.dermatologycare.domain.model.valueobjects.DermatologistId;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record DefineAvailabilityCommand(DermatologistId dermatologistId, DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
}
