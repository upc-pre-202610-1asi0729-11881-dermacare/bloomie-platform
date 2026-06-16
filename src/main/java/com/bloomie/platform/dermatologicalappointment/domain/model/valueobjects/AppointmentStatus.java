package com.bloomie.platform.dermatologicalappointment.domain.model.valueobjects;

/**
 * Lifecycle status of a dermatological appointment.
 *
 * <ul>
 *   <li>{@code SCHEDULED}   – created, awaiting confirmation.</li>
 *   <li>{@code CONFIRMED}   – patient confirmed; ready to start consultation.</li>
 *   <li>{@code IN_PROGRESS} – the associated consultation is currently active.</li>
 *   <li>{@code COMPLETED}   – consultation finished; terminal state.</li>
 *   <li>{@code CANCELLED}   – cancelled by patient; terminal state.</li>
 * </ul>
 */
public enum AppointmentStatus {
    SCHEDULED,
    CONFIRMED,
    IN_PROGRESS,
    COMPLETED,
    CANCELLED
}
