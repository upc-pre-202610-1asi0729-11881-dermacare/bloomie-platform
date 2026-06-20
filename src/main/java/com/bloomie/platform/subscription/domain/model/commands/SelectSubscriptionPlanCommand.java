// SelectSubscriptionPlanCommand.java
package com.bloomie.platform.subscription.domain.model.commands;

public record SelectSubscriptionPlanCommand(
        Long patientId,
        Long planId) {}