package com.bloomie.platform.routinemanagement.application.internal.eventhandlers;

import com.bloomie.platform.routinemanagement.application.commandservices.RoutineCommandService;
import com.bloomie.platform.routinemanagement.application.internal.outboundservices.acl.ExternalSkinAnalysisService;
import com.bloomie.platform.routinemanagement.domain.model.commands.GeneratePersonalizedRoutineCommand;
import com.bloomie.platform.skinanalysis.interfaces.events.PreliminaryDiagnosisGeneratedIntegrationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Listens for the {@link PreliminaryDiagnosisGeneratedIntegrationEvent} published by the Skin Analysis
 * bounded context and triggers routine generation for the patient.
 */
@Service("routinePreliminaryDiagnosisGeneratedEventHandler")
public class PreliminaryDiagnosisGeneratedEventHandler {

    private final RoutineCommandService routineCommandService;
    private final ExternalSkinAnalysisService externalSkinAnalysisService;

    public PreliminaryDiagnosisGeneratedEventHandler(RoutineCommandService routineCommandService,
                                                     ExternalSkinAnalysisService externalSkinAnalysisService) {
        this.routineCommandService = routineCommandService;
        this.externalSkinAnalysisService = externalSkinAnalysisService;
    }

    @EventListener
    public void on(PreliminaryDiagnosisGeneratedIntegrationEvent event) {
        externalSkinAnalysisService.fetchSkinTypeByPatientId(event.patientId()).ifPresent(skinType -> {
            var command = new GeneratePersonalizedRoutineCommand(
                    event.patientId(),
                    event.skinAnalysisId(),
                    skinType);
            routineCommandService.handle(command);
        });
    }
}