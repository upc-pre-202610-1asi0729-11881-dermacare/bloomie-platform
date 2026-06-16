package com.bloomie.platform.dermatologyCare.application.internal.commandservices;

import com.bloomie.platform.dermatologyCare.application.commandservices.DermatologistProfileCommandService;
import com.bloomie.platform.dermatologyCare.domain.model.aggregates.DermatologistProfile;
import com.bloomie.platform.dermatologyCare.domain.model.commands.RegisterDermatologistProfileCommand;
import com.bloomie.platform.dermatologyCare.domain.model.commands.UpdateDermatologistProfileCommand;
import com.bloomie.platform.dermatologyCare.domain.repositories.DermatologistProfileRepository;
import com.bloomie.platform.shared.application.result.ApplicationError;
import com.bloomie.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

/**
 * Application service that handles write operations on the {@link DermatologistProfile} aggregate.
 *
 * <p>Domain events are published by the repository adapter after each save.</p>
 */
@Service
public class DermatologistProfileCommandServiceImpl implements DermatologistProfileCommandService {

    private static final String PROFILE_ALREADY_EXISTS = "dermatology.profile.already.exists";
    private static final String PROFILE_NOT_FOUND = "dermatology.profile.not.found";

    private final DermatologistProfileRepository dermatologistProfileRepository;

    public DermatologistProfileCommandServiceImpl(DermatologistProfileRepository dermatologistProfileRepository) {
        this.dermatologistProfileRepository = dermatologistProfileRepository;
    }

    @Override
    public Result<Long, ApplicationError> handle(RegisterDermatologistProfileCommand command) {
        if (dermatologistProfileRepository.existsByDermatologistId(command.dermatologistId()))
            return Result.failure(ApplicationError.conflict("dermatologist-profile", PROFILE_ALREADY_EXISTS));

        var profile = dermatologistProfileRepository.save(new DermatologistProfile(command));
        return Result.success(profile.getId());
    }

    @Override
    public Result<DermatologistProfile, ApplicationError> handle(UpdateDermatologistProfileCommand command) {
        var result = dermatologistProfileRepository.findById(command.dermatologistProfileId());
        if (result.isEmpty())
            return Result.failure(ApplicationError.notFound("dermatologist-profile", PROFILE_NOT_FOUND));

        var profile = result.get();
        profile.update(command);
        return Result.success(dermatologistProfileRepository.save(profile));
    }
}
