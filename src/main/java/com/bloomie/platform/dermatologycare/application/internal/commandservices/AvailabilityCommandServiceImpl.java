package com.bloomie.platform.dermatologyCare.application.internal.commandservices;

import com.bloomie.platform.dermatologyCare.application.commandservices.AvailabilityCommandService;
import com.bloomie.platform.dermatologyCare.domain.model.aggregates.Availability;
import com.bloomie.platform.dermatologyCare.domain.model.commands.DefineAvailabilityCommand;
import com.bloomie.platform.dermatologyCare.domain.model.commands.UpdateAvailabilityCommand;
import com.bloomie.platform.dermatologyCare.domain.repositories.AvailabilityRepository;
import com.bloomie.platform.shared.application.result.ApplicationError;
import com.bloomie.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

/**
 * Application service that handles write operations on the {@link Availability} aggregate.
 *
 * <p>Domain events are published by the repository adapter after each save.</p>
 */
@Service
public class AvailabilityCommandServiceImpl implements AvailabilityCommandService {

    private static final String AVAILABILITY_ALREADY_DEFINED = "dermatology.availability.already.defined";
    private static final String AVAILABILITY_NOT_FOUND = "dermatology.availability.not.found";

    private final AvailabilityRepository availabilityRepository;

    public AvailabilityCommandServiceImpl(AvailabilityRepository availabilityRepository) {
        this.availabilityRepository = availabilityRepository;
    }

    @Override
    public Result<Long, ApplicationError> handle(DefineAvailabilityCommand command) {
        if (availabilityRepository.existsByDermatologyAndDay(command.dermatologistId(), command.dayOfWeek()))
            return Result.failure(ApplicationError.conflict("availability", AVAILABILITY_ALREADY_DEFINED));

        var availability = availabilityRepository.save(new Availability(command));
        return Result.success(availability.getId());
    }

    @Override
    public Result<Availability, ApplicationError> handle(UpdateAvailabilityCommand command) {
        var result = availabilityRepository.findById(command.availabilityId());
        if (result.isEmpty())
            return Result.failure(ApplicationError.notFound("availability", AVAILABILITY_NOT_FOUND));

        var availability = result.get();
        availability.update(command);
        return Result.success(availabilityRepository.save(availability));
    }
}
