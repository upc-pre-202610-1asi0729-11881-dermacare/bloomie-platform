package com.bloomie.platform.skinanalysis.application.internal.eventhandlers;

import com.bloomie.platform.skinanalysis.application.internal.outboundservices.acl.ExternalSkinProfileService;
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

    public FacialScanSubmittedAnalysisEventHandler(ExternalSkinProfileService externalSkinProfileService) {
        this.externalSkinProfileService = externalSkinProfileService;
    }

    @EventListener
    public void on(FacialScanSubmittedIntegrationEvent event) {
        var skinType    = externalSkinProfileService.fetchSkinTypeByPatientId(event.patientId());
        var sensitivity = externalSkinProfileService.fetchSensitivityByPatientId(event.patientId());

        if (skinType.isEmpty()) {
            log.warn("Skin type not found for patient {}. Analysis cannot proceed.", event.patientId());
            return;
        }

        if (sensitivity.isEmpty()) {
            log.warn("Sensitivity not found for patient {}. Analysis cannot proceed.", event.patientId());
            return;
        }

        // TODO: dispatch AnalyzeSkinScanCommand when SkinAnalysis feature is implemented
        log.info("Facial scan submitted for patient {}. Analysis pending.", event.patientId());
    }
}
