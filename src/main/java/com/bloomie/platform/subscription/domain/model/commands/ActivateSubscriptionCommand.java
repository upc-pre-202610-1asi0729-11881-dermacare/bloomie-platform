package com.bloomie.platform.subscription.domain.model.commands;

public record ActivateSubscriptionCommand(Long patientId,
                                          Long planId) {
}
