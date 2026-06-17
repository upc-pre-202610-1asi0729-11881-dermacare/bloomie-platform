package com.bloomie.platform.skinAnalysis.application.internal.eventhandlers;

import com.bloomie.platform.iam.domain.model.events.UserRegisteredEvent;
import com.bloomie.platform.skinAnalysis.application.internal.outboundservices.acl.ExternalIamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Reacts to {@link UserRegisteredEvent} emitted by the IAM context when a Young Adult registers.
 *
 * <p>Validates that the user exists via the ACL and logs the integration point.
 * The skin profile itself is NOT created automatically — the patient must complete
 * it explicitly from the Skin Profile Form View (Complete Skin Profile command).
 * This handler exists to establish the integration boundary and can be extended
 * to trigger any skin-analysis onboarding logic if needed.</p>
 */
@Service
@Slf4j
public class UserRegisteredEventHandler {

    private final ExternalIamService externalIamService;

    public UserRegisteredEventHandler(ExternalIamService externalIamService) {
        this.externalIamService = externalIamService;
    }

    @EventListener
    public void on(UserRegisteredEvent event) {
        if (!externalIamService.existsUserById(event.userId())) {
            log.warn("Received UserRegisteredEvent for unknown user id: {}", event.userId());
            return;
        }
        log.info("User {} registered — skin profile pending completion by patient.",
                event.userId());
    }
}