package com.bloomie.platform.skinanalysis.application.internal.commandservices;

import com.bloomie.platform.shared.application.result.ApplicationError;
import com.bloomie.platform.shared.application.result.Result;
import com.bloomie.platform.skinanalysis.application.commandservices.SkinProfileCommandService;
import com.bloomie.platform.skinanalysis.application.internal.outboundservices.acl.ExternalIamService;
import com.bloomie.platform.skinanalysis.domain.model.aggregates.SkinProfile;
import com.bloomie.platform.skinanalysis.domain.model.commands.CompleteSkinProfileCommand;
import com.bloomie.platform.skinanalysis.domain.model.commands.UpdateSkinCharacteristicsCommand;
import com.bloomie.platform.skinanalysis.domain.model.valueobjects.PatientId;
import com.bloomie.platform.skinanalysis.domain.repositories.SkinProfileRepository;
import org.springframework.stereotype.Service;

/**
 * Application service that handles write operations on the {@link SkinProfile} aggregate.
 *
 * <p>Domain events are published by the repository adapter after each save.</p>
 */
@Service
public class SkinProfileCommandServiceImpl implements SkinProfileCommandService {

    private static final String PATIENT_NOT_FOUND      = "skin.profile.patient.not.found";
    private static final String PROFILE_ALREADY_EXISTS = "skin.profile.already.exists";
    private static final String PROFILE_NOT_FOUND      = "skin.profile.not.found";

    private final SkinProfileRepository skinProfileRepository;
    private final ExternalIamService externalIamService;

    public SkinProfileCommandServiceImpl(SkinProfileRepository skinProfileRepository,
                                         ExternalIamService externalIamService) {
        this.skinProfileRepository = skinProfileRepository;
        this.externalIamService    = externalIamService;
    }

    @Override
    public Result<Long, ApplicationError> handle(CompleteSkinProfileCommand command) {
        if (skinProfileRepository.existsByPatientId(new PatientId(command.patientId())))
            return Result.failure(ApplicationError.conflict("skin-profile", PROFILE_ALREADY_EXISTS));

        if (externalIamService.fetchPatientById(command.patientId()).isEmpty())
            return Result.failure(ApplicationError.notFound("patient", PATIENT_NOT_FOUND));

        var saved = skinProfileRepository.save(new SkinProfile(command));
        return Result.success(saved.getId());
    }

    @Override
    public Result<Long, ApplicationError> handle(UpdateSkinCharacteristicsCommand command) {
        var result = skinProfileRepository.findById(command.skinProfileId());
        if (result.isEmpty())
            return Result.failure(ApplicationError.notFound("skin-profile", PROFILE_NOT_FOUND));

        var skinProfile = result.get();
        skinProfile.update(command);
        var saved = skinProfileRepository.save(skinProfile);
        return Result.success(saved.getId());
    }
}
