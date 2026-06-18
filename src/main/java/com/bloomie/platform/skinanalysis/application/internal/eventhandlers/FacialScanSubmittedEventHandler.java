package com.bloomie.platform.skinanalysis.application.internal.eventhandlers;

import com.bloomie.platform.skinanalysis.application.commandservices.SkinAnalysisCommandService;
import com.bloomie.platform.skinanalysis.application.queryservices.SkinProfileQueryService;
import com.bloomie.platform.skinanalysis.domain.model.commands.AnalyzeSkinScanCommand;
import com.bloomie.platform.skinanalysis.domain.model.events.FacialScanSubmittedEvent;
import com.bloomie.platform.skinanalysis.domain.model.queries.GetSkinProfileByPatientIdQuery;
import com.bloomie.platform.skinanalysis.domain.model.valueobjects.PatientId;
import com.bloomie.platform.shared.application.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FacialScanSubmittedEventHandler {

    private final SkinProfileQueryService skinProfileQueryService;
    private final SkinAnalysisCommandService skinAnalysisCommandService;

    public FacialScanSubmittedEventHandler(
            SkinProfileQueryService skinProfileQueryService,
            SkinAnalysisCommandService skinAnalysisCommandService) {
        this.skinProfileQueryService = skinProfileQueryService;
        this.skinAnalysisCommandService = skinAnalysisCommandService;
    }

    @EventListener
    public void on(FacialScanSubmittedEvent event) {
        var skinProfile = skinProfileQueryService.handle(
                new GetSkinProfileByPatientIdQuery(new PatientId(event.patientId())));

        if (skinProfile.isEmpty()) {
            log.warn("Skin profile not found for patient {}", event.patientId());
            return;
        }

        var command = new AnalyzeSkinScanCommand(
                event.facialScanId(),
                event.patientId(),
                skinProfile.get().getSkinType().name(),
                skinProfile.get().getSensitivity().name());

        var result = skinAnalysisCommandService.handle(command);

        if (result instanceof Result.Failure(var error)) {
            log.warn("Failed to analyze skin scan {} for patient {}: {}",
                    event.facialScanId(), event.patientId(), error.message());
        }
    }
}