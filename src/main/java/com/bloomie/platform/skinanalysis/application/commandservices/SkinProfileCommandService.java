package com.bloomie.platform.skinanalysis.application.commandservices;

import com.bloomie.platform.shared.application.result.ApplicationError;
import com.bloomie.platform.shared.application.result.Result;
import com.bloomie.platform.skinanalysis.domain.model.commands.CompleteSkinProfileCommand;
import com.bloomie.platform.skinanalysis.domain.model.commands.UpdateSkinCharacteristicsCommand;

/**
 * Application service interface for all write operations on the
 * {@link com.bloomie.platform.skinAnalysis.domain.model.aggregates.SkinProfile} aggregate.
 */
public interface SkinProfileCommandService {

    /**
     * Completes the skin profile for the given patient.
     *
     * @param command the command carrying the patient id and skin questionnaire answers
     * @return the id of the newly created skin profile, or an error
     */
    Result<Long, ApplicationError> handle(CompleteSkinProfileCommand command);

    /**
     * Updates the skin characteristics of an existing skin profile.
     *
     * @param command the command carrying the profile id and updated answers
     * @return the id of the updated skin profile, or an error
     */
    Result<Long, ApplicationError> handle(UpdateSkinCharacteristicsCommand command);
}
