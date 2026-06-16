package com.bloomie.platform.dermatologyCare.domain.repositories;

import com.bloomie.platform.dermatologyCare.domain.model.aggregates.Availability;
import com.bloomie.platform.dermatologyCare.domain.model.valueobjects.DermatologistId;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

public interface AvailabilityRepository {
    Optional<Availability> findById(Long id);
    List<Availability> findAllByDermatologistId(DermatologistId dermatologistId);
    List<Availability> findAllByDermatologistAndDay(DermatologistId dermatologistId, DayOfWeek day);
    Availability save(Availability availability);
    boolean existsByDermatologyAndDay(DermatologistId dermatologistId, DayOfWeek day);
}
