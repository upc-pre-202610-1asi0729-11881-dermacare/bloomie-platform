package com.bloomie.platform.subscription.domain.model.commands;

/**
 * Command that triggers the initial seeding of the plan catalog.
 * Issued by the {@link com.bloomie.platform.subscription.application.internal.eventhandlers.ApplicationReadyEventHandler}
 * on application startup.
 */
public record SeedPlansCommand() {}