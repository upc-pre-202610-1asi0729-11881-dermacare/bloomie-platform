package com.bloomie.platform.skinAnalysis.application.internal.commandservices;

import com.bloomie.platform.shared.application.result.ApplicationError;
import com.bloomie.platform.shared.application.result.Result;
import com.bloomie.platform.skinAnalysis.application.commandservices.SkinProfileCommandService;
import com.bloomie.platform.skinAnalysis.application.internal.outboundservices.acl.ExternalIamService;
import com.bloomie.platform.skinAnalysis.domain.model.aggregates.SkinProfile;
import com.bloomie.platform.skinAnalysis.domain.model.commands.CompleteSkinProfileCommand;
import com.bloomie.platform.skinAnalysis.domain.model.commands.UpdateSkinCharacteristicsCommand;
import com.bloomie.platform.skinAnalysis.domain.model.valueobjects.PatientId;
import com.bloomie.platform.skinAnalysis.domain.repositories.SkinProfileRepository;
import org.springframework.stereotype.Service;

@Service
public class SkinProfileCommandServiceImpl implements SkinProfileCommandService {

    private static final String PATIENT_NOT_FOUND      = "skin_analysis.patient.not_found";
    private static final String PROFILE_ALREADY_EXISTS = "skin_analysis.skin_profile.already_exists";
    private static final String PROFILE_NOT_FOUND      = "skin_analysis.skin_profile.not_found";

    private final SkinProfileRepository skinProfileRepository;
    private final ExternalIamService externalIamService;

    public SkinProfileCommandServiceImpl(SkinProfileRepository skinProfileRepository,
                                         ExternalIamService externalIamService) {
        this.skinProfileRepository = skinProfileRepository;
        this.externalIamService    = externalIamService;
    }

    @Override
    public Result<SkinProfile, ApplicationError> handle(CompleteSkinProfileCommand command) {
        if (!externalIamService.existsUserById(command.patient_id())) {
            return Result.failure(ApplicationError.notFound("patient", PATIENT_NOT_FOUND));
        }
        var patient_id = new PatientId(command.patient_id());
        if (skinProfileRepository.existsByPatientId(patient_id)) {
            return Result.failure(ApplicationError.conflict("skin_profile", PROFILE_ALREADY_EXISTS));
        }
        return Result.success(skinProfileRepository.save(new SkinProfile(command)));
    }

    @Override
    public Result<SkinProfile, ApplicationError> handle(UpdateSkinCharacteristicsCommand command) {
        var skin_profile = skinProfileRepository.findById(command.skin_profile_id());
        if (skin_profile.isEmpty()) {
            return Result.failure(ApplicationError.notFound("skin_profile", PROFILE_NOT_FOUND));
        }
        skin_profile.get().updateCharacteristics(command);
        skin_profile.get().onCharacteristicsUpdated();
        return Result.success(skinProfileRepository.save(skin_profile.get()));
    }
}