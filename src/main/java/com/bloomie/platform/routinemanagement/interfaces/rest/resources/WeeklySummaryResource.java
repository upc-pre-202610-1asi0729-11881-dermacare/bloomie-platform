package com.bloomie.platform.routinemanagement.interfaces.rest.resources;

/**
 * Resource representing the weekly routine completion summary for a patient.
 *
 * @param patientId      the identifier of the patient
 * @param weekStart      the ISO 8601 date string for Monday of the current week (e.g. "2026-06-15")
 * @param weekEnd        the ISO 8601 date string for Sunday of the current week (e.g. "2026-06-21")
 * @param completedDays  the number of days the patient completed their routine this week
 * @param missedDays     the number of days the patient skipped their routine this week
 * @param completionRate the percentage of days completed, rounded to one decimal place (e.g. 71.4)
 */
public record WeeklySummaryResource(
        Long patientId,
        String weekStart,
        String weekEnd,
        int completedDays,
        int missedDays,
        Double completionRate) {
}
