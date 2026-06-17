package com.bloomie.platform.skinanalysis.application.queryservices;

import com.bloomie.platform.skinanalysis.domain.model.aggregates.FacialScan;
import com.bloomie.platform.skinanalysis.domain.model.queries.GetFacialScanByIdQuery;
import com.bloomie.platform.skinanalysis.domain.model.queries.GetFacialScansByPatientIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Application service interface for all read operations on the
 * {@link FacialScan} aggregate.
 */
public interface FacialScanQueryService {

    /**
     * Returns the facial scan with the given id, or empty if not found.
     *
     * @param query the query carrying the facial scan id
     * @return an optional containing the facial scan
     */
    Optional<FacialScan> handle(GetFacialScanByIdQuery query);

    /**
     * Returns all facial scans belonging to the given patient.
     *
     * @param query the query carrying the patient value object
     * @return a list of facial scans (may be empty)
     */
    List<FacialScan> handle(GetFacialScansByPatientIdQuery query);
}
