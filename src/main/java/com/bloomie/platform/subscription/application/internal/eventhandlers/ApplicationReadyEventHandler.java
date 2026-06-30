package com.bloomie.platform.subscription.application.internal.eventhandlers;

import com.bloomie.platform.subscription.application.commandservices.PlanCommandService;
import com.bloomie.platform.subscription.domain.model.commands.SeedPlansCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * Application lifecycle handler that seeds subscription plans
 * when the Spring context is fully ready.
 */
@Service("subscriptionApplicationReadyEventHandler")
public class ApplicationReadyEventHandler {

    private static final Logger log = LoggerFactory.getLogger(ApplicationReadyEventHandler.class);

    private final PlanCommandService planCommandService;

    public ApplicationReadyEventHandler(PlanCommandService planCommandService) {
        this.planCommandService = planCommandService;
    }

    @EventListener
    public void on(ApplicationReadyEvent event) {
        var applicationName = event.getApplicationContext().getId();
        log.info("Starting to verify if plan seeding is needed for {} at {}",
                applicationName, currentTimestamp());
        planCommandService.handle(new SeedPlansCommand());
        log.info("Plan seeding verification finished for {} at {}",
                applicationName, currentTimestamp());
    }

    private Timestamp currentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }
}