package com.bloomie.platform.skinanalysis.application.internal.eventhandlers;

import com.bloomie.platform.skinanalysis.application.commandservices.SkinAnalysisCommandService;
import com.bloomie.platform.skinanalysis.application.internal.outboundservices.acl.ExternalSkinProfileService;
import com.bloomie.platform.skinanalysis.domain.model.commands.AnalyzeSkinScanCommand;
import com.bloomie.platform.skinanalysis.interfaces.events.FacialScanSubmittedIntegrationEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Reacts to a {@link FacialScanSubmittedIntegrationEvent} and initiates the skin analysis
 * pipeline by fetching the patient's skin profile data and dispatching the analysis command.
 */
@Service
public class FacialScanSubmittedAnalysisEventHandler {

    private static final Logger log = LoggerFactory.getLogger(FacialScanSubmittedAnalysisEventHandler.class);

    private final ExternalSkinProfileService externalSkinProfileService;
    private final SkinAnalysisCommandService skinAnalysisCommandService;

    public FacialScanSubmittedAnalysisEventHandler(ExternalSkinProfileService externalSkinProfileService,
                                                   SkinAnalysisCommandService skinAnalysisCommandService) {
        this.externalSkinProfileService = externalSkinProfileService;
        this.skinAnalysisCommandService = skinAnalysisCommandService;
    }

    @EventListener
    public void on(FacialScanSubmittedIntegrationEvent event) {
        var skinType    = externalSkinProfileService.fetchSkinTypeByPatientId(event.patientId());
        var sensitivity = externalSkinProfileService.fetchSensitivityByPatientId(event.patientId());

        if (skinType.isEmpty() || sensitivity.isEmpty()) {
            log.warn("Skin profile not found for patient {}", event.patientId());
            return;
        }

        var command = new AnalyzeSkinScanCommand(
                event.facialScanId(),
                event.patientId(),
                skinType.get(),
                sensitivity.get());

        var result = skinAnalysisCommandService.handle(command);

        if (result.isFailure()) {
            log.warn("Failed to analyze skin scan {} for patient {}", event.facialScanId(), event.patientId());
        }
    }
}
