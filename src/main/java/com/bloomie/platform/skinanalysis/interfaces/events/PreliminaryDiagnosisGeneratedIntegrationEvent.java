package com.bloomie.platform.skinanalysis.interfaces.events;

/**
 * Integration event published by the {@code skinAnalysis} bounded context when a
 * preliminary diagnosis has been generated from a skin analysis.
 *
 * <p>This is the <em>published language</em> of the skin analysis context.
 * Other bounded contexts should listen to this event rather than to the internal
 * {@link com.bloomie.platform.skinanalysis.domain.model.events.PreliminaryDiagnosisGeneratedEvent}.</p>
 *
 * @param skinAnalysisId the id of the completed skin analysis
 * @param patientId      the IAM user id of the patient
 * @param skinType       the patient's skin type used for the analysis
 */
public record PreliminaryDiagnosisGeneratedIntegrationEvent(
        Long skinAnalysisId,
        Long patientId,
        String skinType) {
}
