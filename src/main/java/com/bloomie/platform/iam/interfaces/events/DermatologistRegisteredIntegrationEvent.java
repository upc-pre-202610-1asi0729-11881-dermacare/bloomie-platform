package com.bloomie.platform.iam.interfaces.events;

import com.bloomie.platform.iam.domain.model.aggregates.User;

public record DermatologistRegisteredIntegrationEvent(
        Long userId,
        String firstName,
        String lastName,
        String email) {

    public static DermatologistRegisteredIntegrationEvent from(User user) {
        return new DermatologistRegisteredIntegrationEvent(
                user.getId(),
                user.getName().firstName(),
                user.getName().lastName(),
                user.getEmail());
    }
}