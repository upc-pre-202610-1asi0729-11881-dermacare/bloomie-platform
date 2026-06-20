package com.bloomie.platform.skinanalysis.application.internal.commandservices;

import com.bloomie.platform.shared.application.result.ApplicationError;
import com.bloomie.platform.shared.application.result.Result;
import com.bloomie.platform.skinanalysis.application.commandservices.FacialScanCommandService;
import com.bloomie.platform.skinanalysis.application.internal.outboundservices.acl.ExternalIamService;
import com.bloomie.platform.skinanalysis.domain.model.aggregates.FacialScan;
import com.bloomie.platform.skinanalysis.domain.model.commands.StartFacialScanCommand;
import com.bloomie.platform.skinanalysis.domain.model.commands.SubmitFacialScanCommand;
import com.bloomie.platform.skinanalysis.domain.model.valueobjects.FacialScanStatus;
import com.bloomie.platform.skinanalysis.domain.repositories.FacialScanRepository;
import org.springframework.stereotype.Service;

/**
 * Application service that handles write operations on the {@link FacialScan} aggregate.
 *
 * <p>Domain events are published by the repository adapter after each save.</p>
 */
@Service
public class FacialScanCommandServiceImpl implements FacialScanCommandService {

    private static final String PATIENT_NOT_FOUND      = "skin.profile.patient.not.found";
    private static final String PATIENT_ID_INVALID     = "skin.facial.scan.patient.id.invalid";
    private static final String SCAN_NOT_FOUND         = "skin.facial.scan.not.found";
    private static final String SCAN_ALREADY_SUBMITTED = "skin.facial.scan.already.submitted";
    private static final String PHOTO_URL_BLANK        = "skin.facial.scan.photo.url.blank";

    private final FacialScanRepository facialScanRepository;
    private final ExternalIamService externalIamService;

    public FacialScanCommandServiceImpl(FacialScanRepository facialScanRepository,
                                        ExternalIamService externalIamService) {
        this.facialScanRepository = facialScanRepository;
        this.externalIamService = externalIamService;
    }

    @Override
    public Result<Long, ApplicationError> handle(StartFacialScanCommand command) {
        if (command.patientId() == null || command.patientId() < 1)
            return Result.failure(ApplicationError.validationError("patient-id", PATIENT_ID_INVALID));

        if (externalIamService.fetchPatientById(command.patientId()).isEmpty())
            return Result.failure(ApplicationError.notFound("patient", PATIENT_NOT_FOUND));

        var saved = facialScanRepository.save(new FacialScan(command));
        return Result.success(saved.getId());
    }

    @Override
    public Result<Long, ApplicationError> handle(SubmitFacialScanCommand command) {
        if (command.photoUrl() == null || command.photoUrl().isBlank())
            return Result.failure(ApplicationError.validationError("photo-url", PHOTO_URL_BLANK));

        var result = facialScanRepository.findById(command.facialScanId());
        if (result.isEmpty())
            return Result.failure(ApplicationError.notFound("facial-scan", SCAN_NOT_FOUND));

        var facialScan = result.get();
        if (facialScan.getStatus() != FacialScanStatus.STARTED)
            return Result.failure(ApplicationError.conflict("facial-scan", SCAN_ALREADY_SUBMITTED));

        facialScan.submit(command.photoUrl());
        var saved = facialScanRepository.save(facialScan);
        return Result.success(saved.getId());
    }
}
