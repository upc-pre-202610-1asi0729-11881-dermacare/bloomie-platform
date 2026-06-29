package com.bloomie.platform.iam.application.internal.eventhandlers;

import com.bloomie.platform.iam.application.commandservices.RoleCommandService;
import com.bloomie.platform.iam.domain.model.commands.SeedRolesCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * Application lifecycle handler that seeds IAM roles when the Spring context is fully ready.
 *
 * <p>Listens for {@link ApplicationReadyEvent} — fired after the context is refreshed
 * and all runners have been called — so the datasource and all beans are guaranteed
 * to be available before the seed operation runs.</p>
 */
// Explicit name avoids conflict with other ApplicationReadyEventHandler beans in sibling bounded contexts
@Service("iamApplicationReadyEventHandler")
@Slf4j
public class ApplicationReadyEventHandler {

    private final RoleCommandService roleCommandService;

    public ApplicationReadyEventHandler(RoleCommandService roleCommandService) {
        this.roleCommandService = roleCommandService;
    }

    /**
     * Triggers role-seeding verification once the application is fully started.
     *
     * @param event Spring Boot application-ready lifecycle event
     */
    @EventListener
    public void on(ApplicationReadyEvent event) {
        var applicationName = event.getApplicationContext().getId();
        log.info("Starting to verify if roles seeding is needed for {} at {}", applicationName, currentTimestamp());
        var seedRolesCommand = new SeedRolesCommand();
        roleCommandService.handle(seedRolesCommand);
        log.info("Roles seeding verification finished for {} at {}", applicationName, currentTimestamp());
    }

    private Timestamp currentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }
}
