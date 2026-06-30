package com.bloomie.platform.subscription.application.commandservices;

import com.bloomie.platform.subscription.domain.model.commands.SeedPlansCommand;

/**
 * Application service port for plan catalog commands.
 */
public interface PlanCommandService {

    /**
     * Seeds the initial plan catalog if no plans exist.
     *
     * @param command the seed plans command
     */
    void handle(SeedPlansCommand command);
}