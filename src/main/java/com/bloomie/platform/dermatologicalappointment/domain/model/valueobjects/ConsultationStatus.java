package com.bloomie.platform.dermatologicalappointment.domain.model.valueobjects;

/**
 * Lifecycle status of a dermatological consultation.
 *
 * <ul>
 *   <li>{@code PENDING}     – consultation created, not yet started by the dermatologist.</li>
 *   <li>{@code IN_PROGRESS} – dermatologist has started the session.</li>
 *   <li>{@code COMPLETED}   – dermatologist has finished the session; terminal state.</li>
 *   <li>{@code CANCELLED}   – consultation cancelled; terminal state.</li>
 * </ul>
 */
public enum ConsultationStatus {
    PENDING,
    IN_PROGRESS,
    COMPLETED,
    CANCELLED
}
