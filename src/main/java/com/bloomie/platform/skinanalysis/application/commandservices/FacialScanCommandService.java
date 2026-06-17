package com.bloomie.platform.skinanalysis.application.commandservices;

import com.bloomie.platform.shared.application.result.ApplicationError;
import com.bloomie.platform.shared.application.result.Result;
import com.bloomie.platform.skinanalysis.domain.model.commands.StartFacialScanCommand;

/**
 * Application service interface for all write operations on the
 * {@link com.bloomie.platform.skinanalysis.domain.model.aggregates.FacialScan} aggregate.
 */
public interface FacialScanCommandService {

    /**
     * Starts a new facial scan session for the given patient.
     *
     * @param command the command carrying the patient id
     * @return the id of the newly created facial scan, or an error
     */
    Result<Long, ApplicationError> handle(StartFacialScanCommand command);
}
