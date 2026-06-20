package com.bloomie.platform.skinanalysis.domain.model.events;

import com.bloomie.platform.skinanalysis.domain.model.aggregates.SkinAnalysis;

/**
 * Domain event raised when a preliminary diagnosis has been generated from a skin analysis.
 *
 * <p>Consumed by {@link com.bloomie.platform.skinanalysis.application.internal.eventhandlers.PreliminaryDiagnosisGeneratedEventHandler},
 * which re-publishes it as a
 * {@link com.bloomie.platform.skinanalysis.interfaces.events.PreliminaryDiagnosisGeneratedIntegrationEvent}.</p>
 *
 * @param skinAnalysisId the id of the completed skin analysis
 * @param patientId      the IAM user id of the patient
 * @param overallScore   the computed overall skin score
 * @param skinType       the patient's skin type used for the analysis
 */
public record PreliminaryDiagnosisGeneratedEvent(
        Long skinAnalysisId,
        Long patientId,
        Double overallScore,
        String skinType) {

    /** Factory method to build the event from the saved aggregate. */
    public static PreliminaryDiagnosisGeneratedEvent from(SkinAnalysis skinAnalysis) {
        return new PreliminaryDiagnosisGeneratedEvent(
                skinAnalysis.getId(),
                skinAnalysis.getPatientId(),
                skinAnalysis.getOverallScore(),
                skinAnalysis.getSkinType());
    }
}
