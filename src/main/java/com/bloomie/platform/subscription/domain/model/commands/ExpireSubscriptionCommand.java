package com.bloomie.platform.subscription.domain.model.commands;

/**
 * Command to mark an active subscription as expired.
 *
 * <p>Typically triggered when the billing period ends and the renewal payment
 * could not be processed successfully.</p>
 *
 * @param subscriptionId unique identifier of the subscription to expire
 */
public record ExpireSubscriptionCommand(Long subscriptionId) {}
