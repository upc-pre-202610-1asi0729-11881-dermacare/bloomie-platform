package com.bloomie.platform.subscription.domain.model.commands;

/**
 * Command to change the plan of an existing subscription.
 *
 * @param subscriptionId unique identifier of the subscription to update
 * @param newPlanId      identifier of the plan to switch the subscription to
 */
public record ChangeSubscriptionPlanCommand(Long subscriptionId, Long newPlanId) {}
