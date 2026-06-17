package com.bloomie.platform.skinAnalysis.application.internal.eventhandlers;

import com.bloomie.platform.skinAnalysis.domain.model.events.SkinProfileCompletedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Reacts to {@link SkinProfileCompletedEvent} raised by the {@code SkinProfile} aggregate.
 *
 * <p>Current responsibility: audit log. Can be extended to trigger downstream
 * integration events (e.g. notifying a recommendation engine) once those
 * bounded contexts are defined.</p>
 */
@Service
@Slf4j
public class SkinProfileCompletedEventHandler {

    @EventListener
    public void on(SkinProfileCompletedEvent event) {
        log.info("Skin profile {} completed for patient {} — skin_type: {}, skin_tone: {}.",
                event.skin_profile_id(),
                event.patient_id(),
                event.skin_type(),
                event.skin_tone());
    }
}