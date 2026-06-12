package com.bloomie.platform.dermatologicalAppointment.domain.model.commands;

/**
 * Command to record the final diagnosis and recommendations during an ongoing consultation.
 *
 * <p>Issued by the dermatologist at the end of the clinical session. Persists both the
 * clinical observations and the treatment recommendations on the consultation aggregate.</p>
 *
 * @param consultationId  the id of the consultation being finalised
 * @param notes           the dermatologist's clinical observations (may be blank, max 5 000 chars)
 * @param recommendations the treatment recommendations (may be blank, max 5 000 chars)
 */
public record RecordDermatologicalDiagnosisCommand(
        Long consultationId,
        String notes,
        String recommendations) {
}
