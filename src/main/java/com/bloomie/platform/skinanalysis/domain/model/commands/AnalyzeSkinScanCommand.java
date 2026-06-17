package com.bloomie.platform.skinanalysis.domain.model.commands;

/**
 * Command to trigger a skin analysis from a submitted facial scan.
 *
 * @param facialScanId the id of the submitted facial scan
 * @param patientId    the IAM user id of the patient
 * @param skinType     the patient's skin type name (e.g. DRY, OILY, COMBINATION, SENSITIVE)
 * @param sensitivity  the patient's skin sensitivity level name (e.g. HIGH, MEDIUM, LOW)
 */
public record AnalyzeSkinScanCommand(
        Long facialScanId,
        Long patientId,
        String skinType,
        String sensitivity) {
}
