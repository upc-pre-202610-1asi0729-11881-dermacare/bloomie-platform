package com.bloomie.platform.dermatologicalappointment.domain.model.commands;

/**
 * Command to progressively save the dermatologist's clinical notes during an ongoing consultation.
 *
 * <p>Issued by the dermatologist mid-session to persist partial observations without
 * finalising the consultation. The notes may be blank (incremental saves are allowed).</p>
 *
 * @param consultationId the id of the active consultation
 * @param notes          the current clinical notes text (may be blank, max 5 000 chars)
 */
public record SaveNotesCommand(Long consultationId, String notes) {
}
