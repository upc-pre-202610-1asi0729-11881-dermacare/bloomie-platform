package com.bloomie.platform.subscription.domain.model.commands;

/**
 * Command to cancel an existing subscription.
 *
 * @param subscriptionId unique identifier of the subscription to cancel
 */
public record CancelSubscriptionCommand(Long subscriptionId) {}
