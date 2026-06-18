package com.bloomie.platform.routinemanagement.domain.model.commands;

/**
 * Command to generate a new personalized skincare routine based on a patient's skin analysis.
 *
 * @param patientId      the identifier of the patient
 * @param skinAnalysisId the identifier of the skin analysis that triggered this routine
 * @param skinType       the patient's skin type (OILY, DRY, SENSITIVE, COMBINATION, NORMAL)
 */
public record GeneratePersonalizedRoutineCommand(Long patientId, Long skinAnalysisId, String skinType) {
}