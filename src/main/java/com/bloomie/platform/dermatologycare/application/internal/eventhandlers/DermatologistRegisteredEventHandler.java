package com.bloomie.platform.dermatologycare.application.internal.eventhandlers;

import com.bloomie.platform.dermatologycare.application.commandservices.DermatologistProfileCommandService;
import com.bloomie.platform.dermatologycare.domain.model.commands.RegisterDermatologistProfileCommand;
import com.bloomie.platform.dermatologycare.domain.model.valueobjects.DermatologistId;
import com.bloomie.platform.iam.interfaces.events.DermatologistRegisteredIntegrationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Reacts to {@link DermatologistRegisteredIntegrationEvent} emitted by the IAM context.
 *
 * <p>Automatically creates a minimal {@code DermatologistProfile} so the dermatologist
 * can later enrich it with specialty, license number, and contact details.</p>
 */
@Service("dermatologyCareProfileCreatedEventHandler")
@Slf4j
public class DermatologistRegisteredEventHandler {

    private final DermatologistProfileCommandService dermatologistProfileCommandService;

    public DermatologistRegisteredEventHandler(DermatologistProfileCommandService dermatologistProfileCommandService) {
        this.dermatologistProfileCommandService = dermatologistProfileCommandService;
    }

    @EventListener
    public void on(DermatologistRegisteredIntegrationEvent event) {
        var command = new RegisterDermatologistProfileCommand(
                new DermatologistId(event.userId()),
                event.firstName(),
                event.lastName());
        var result = dermatologistProfileCommandService.handle(command);

        if (result instanceof com.bloomie.platform.shared.application.result.Result.Failure(var error)) {
            log.warn("Failed to create dermatologist profile for user {}: {}",
                    event.userId(), error.message());
        }
    }
}
