package com.bloomie.platform.skinAnalysis.application.commandservices;

import com.bloomie.platform.shared.application.result.ApplicationError;
import com.bloomie.platform.shared.application.result.Result;
import com.bloomie.platform.skinAnalysis.domain.model.aggregates.SkinProfile;
import com.bloomie.platform.skinAnalysis.domain.model.commands.CompleteSkinProfileCommand;

/**
 * Application service interface for all write operations on the {@link SkinProfile} aggregate.
 */
public interface SkinProfileCommandService {

    Result<SkinProfile, ApplicationError> handle(CompleteSkinProfileCommand command);
}
