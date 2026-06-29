package com.bloomie.platform.subscription.domain.model.commands;

/**
 * Command to renew an active subscription by extending its end date
 * according to the associated plan's duration.
 *
 * @param subscriptionId unique identifier of the subscription to renew
 */
public record RenewSubscriptionCommand(Long subscriptionId) {}
