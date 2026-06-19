package com.bloomie.platform.productdiscovery.application.internal.eventhandlers;

import com.bloomie.platform.productdiscovery.application.commandservices.ProductCommandService;
import com.bloomie.platform.productdiscovery.domain.model.commands.SeedProductsCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * Application lifecycle handler that ensures the product catalog is seeded when the application is ready.
 */
@Service
@Slf4j
public class ApplicationReadyEventHandler {

    private final ProductCommandService productCommandService;

    public ApplicationReadyEventHandler(ProductCommandService productCommandService) {
        this.productCommandService = productCommandService;
    }

    /**
     * Handles the Spring application-ready event and triggers product catalog seeding verification.
     *
     * @param event Spring Boot readiness event
     */
    @EventListener
    public void on(ApplicationReadyEvent event) {
        var applicationName = event.getApplicationContext().getId();
        log.info("Starting to verify if product catalog seeding is needed for {} at {}", applicationName, currentTimestamp());
        productCommandService.handle(new SeedProductsCommand());
        log.info("Product catalog seeding verification finished for {} at {}", applicationName, currentTimestamp());
    }

    private Timestamp currentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }
}
