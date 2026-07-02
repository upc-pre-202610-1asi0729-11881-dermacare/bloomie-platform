package com.bloomie.platform.productdiscovery.application.internal.eventhandlers;

import com.bloomie.platform.productdiscovery.application.commandservices.ProductCommandService;
import com.bloomie.platform.productdiscovery.application.commandservices.ProductCompatibilityCommandService;
import com.bloomie.platform.productdiscovery.domain.model.commands.GenerateProductCompatibilityCommand;
import com.bloomie.platform.productdiscovery.domain.model.commands.SeedProductsCommand;
import com.bloomie.platform.productdiscovery.domain.repositories.ProductCompatibilityRepository;
import com.bloomie.platform.productdiscovery.domain.repositories.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * Application lifecycle handler that ensures the product catalog and compatibility
 * evaluations are seeded when the application is ready.
 */
// Explicit name avoids conflict with other ApplicationReadyEventHandler beans in sibling bounded contexts
@Service("productDiscoveryApplicationReadyEventHandler")
@Slf4j
public class ApplicationReadyEventHandler {

    private static final List<String> SKIN_TYPES =
            List.of("OILY", "DRY", "SENSITIVE", "COMBINATION", "NORMAL");

    private final ProductCommandService productCommandService;
    private final ProductCompatibilityCommandService productCompatibilityCommandService;
    private final ProductRepository productRepository;
    private final ProductCompatibilityRepository productCompatibilityRepository;

    /**
     * Constructs the handler with all required services and repositories.
     *
     * @param productCommandService              command service for seeding the product catalog
     * @param productCompatibilityCommandService command service for generating compatibility evaluations
     * @param productRepository                  domain repository for reading seeded products
     * @param productCompatibilityRepository     domain repository for checking existing compatibilities
     */
    public ApplicationReadyEventHandler(
            ProductCommandService productCommandService,
            ProductCompatibilityCommandService productCompatibilityCommandService,
            ProductRepository productRepository,
            ProductCompatibilityRepository productCompatibilityRepository) {
        this.productCommandService = productCommandService;
        this.productCompatibilityCommandService = productCompatibilityCommandService;
        this.productRepository = productRepository;
        this.productCompatibilityRepository = productCompatibilityRepository;
    }

    /**
     * Handles the Spring application-ready event.
     * First ensures the product catalog is seeded, then generates AI compatibility
     * evaluations for every product × skin-type combination if not yet present.
     *
     * @param event Spring Boot readiness event
     */
    @EventListener
    public void on(ApplicationReadyEvent event) {
        var applicationName = event.getApplicationContext().getId();
        log.info("Starting to verify if product catalog seeding is needed for {} at {}",
                applicationName, currentTimestamp());
        productCommandService.handle(new SeedProductsCommand());
        log.info("Product catalog seeding verification finished for {} at {}",
                applicationName, currentTimestamp());

        seedCompatibilitiesIfNeeded(applicationName);
    }

    /**
     * Generates compatibility evaluations for all products × skin types if none exist yet.
     * A 500 ms delay is inserted between Gemini API calls to respect rate limits.
     *
     * @param applicationName the Spring application context id, used for log messages
     */
    private void seedCompatibilitiesIfNeeded(String applicationName) {
        if (productCompatibilityRepository.count() > 0) {
            log.info("Product compatibility data already seeded — skipping for {}", applicationName);
            return;
        }

        var products = productRepository.findAll();
        log.info("Generating compatibility evaluations for {} products × {} skin types for {}",
                products.size(), SKIN_TYPES.size(), applicationName);

        for (var product : products) {
            for (var skinType : SKIN_TYPES) {
                try {
                    productCompatibilityCommandService.handle(new GenerateProductCompatibilityCommand(
                            product.getId(),
                            product.getName(),
                            product.getCategory().name(),
                            skinType
                    ));
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    log.warn("Compatibility seeding interrupted for product {} / {}", product.getId(), skinType);
                } catch (Exception e) {
                    log.error("Failed to generate compatibility for product {} / {}: {}",
                            product.getId(), skinType, e.getMessage());
                }
            }
        }

        log.info("Product compatibility seeding finished for {} at {}", applicationName, currentTimestamp());
    }

    private Timestamp currentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }
}
