package com.bloomie.platform.skinanalysis.application.internal.queryservices;

import com.bloomie.platform.skinanalysis.application.queryservices.FacialScanQueryService;
import com.bloomie.platform.skinanalysis.domain.model.aggregates.FacialScan;
import com.bloomie.platform.skinanalysis.domain.model.queries.GetFacialScanByIdQuery;
import com.bloomie.platform.skinanalysis.domain.model.queries.GetFacialScansByPatientIdQuery;
import com.bloomie.platform.skinanalysis.domain.repositories.FacialScanRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Application service that handles all read operations on the {@link FacialScan} aggregate.
 *
 * <p>Delegates directly to the {@link FacialScanRepository} domain port without any
 * mutation or event publication.</p>
 */
@Service
public class FacialScanQueryServiceImpl implements FacialScanQueryService {

    private final FacialScanRepository facialScanRepository;

    public FacialScanQueryServiceImpl(FacialScanRepository facialScanRepository) {
        this.facialScanRepository = facialScanRepository;
    }

    @Override
    public Optional<FacialScan> handle(GetFacialScanByIdQuery query) {
        return facialScanRepository.findById(query.facialScanId());
    }

    @Override
    public List<FacialScan> handle(GetFacialScansByPatientIdQuery query) {
        return facialScanRepository.findAllByPatientId(query.patientId());
    }
}
