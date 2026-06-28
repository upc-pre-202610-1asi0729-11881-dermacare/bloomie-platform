package com.bloomie.platform.intelligentsupport.domain.model.queries;

/**
 * Query to retrieve a support query session belonging to a specific patient
 * that is currently in a given lifecycle status.
 *
 * @param patientId the IAM user id of the patient
 * @param status    the {@code SupportQueryStatus} name to filter by (e.g. {@code "IN_PROGRESS"})
 */
public record GetSupportQueryByPatientIdAndStatusQuery(Long patientId, String status) {
}
