package com.bloomie.platform.skinAnalysis.domain.model.queries;

/**
 * Query to retrieve the skin profile belonging to a specific patient.
 *
 * @param patient_id the IAM user id of the patient whose skin profile is requested
 */
public record GetSkinProfileByPatientIdQuery(Long patient_id) {
}