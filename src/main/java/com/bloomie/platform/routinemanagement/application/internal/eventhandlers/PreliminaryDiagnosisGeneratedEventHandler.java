package com.bloomie.platform.routinemanagement.application.internal.eventhandlers;

import com.bloomie.platform.routinemanagement.application.commandservices.RoutineCommandService;
import com.bloomie.platform.routinemanagement.domain.model.commands.GeneratePersonalizedRoutineCommand;
import com.bloomie.platform.shared.application.result.Result;
import com.bloomie.platform.skinanalysis.interfaces.events.PreliminaryDiagnosisGeneratedIntegrationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Listens for the {@link PreliminaryDiagnosisGeneratedIntegrationEvent} published by the Skin Analysis
 * bounded context and triggers routine generation for the patient.
 *
 * <p>Note: this handler references {@code skinAnalysis.interfaces.events} — the published language
 * of the skin analysis context — never an internal domain type from that context.</p>
 *
 * <p>Because Spring's {@link org.springframework.context.event.EventListener} dispatches events
 * synchronously by default, the routine is fully persisted before control returns to the caller.</p>
 */
@Service("routinePreliminaryDiagnosisGeneratedEventHandler")
@Slf4j
public class PreliminaryDiagnosisGeneratedEventHandler {

    private final RoutineCommandService routineCommandService;

    public PreliminaryDiagnosisGeneratedEventHandler(RoutineCommandService routineCommandService) {
        this.routineCommandService = routineCommandService;
    }

    @EventListener
    public void on(PreliminaryDiagnosisGeneratedIntegrationEvent event) {
        var command = new GeneratePersonalizedRoutineCommand(
                event.patientId(),
                event.skinAnalysisId(),
                event.skinType());

        var result = routineCommandService.handle(command);

        if (result instanceof Result.Failure(var error)) {
            log.warn("Failed to generate routine for patient {}: {}",
                    event.patientId(), error.message());
        }
    }
}