package com.bloomie.platform.intelligentsupport.domain.model.valueobjects;

public record SkinProfileId(Long skinProfileId) {
    private static final String INVALID_MESSAGE_KEY = "intelligent.support.skin.profile.id.invalid";
    public SkinProfileId {
        if (skinProfileId == null || skinProfileId < 1)
            throw new IllegalArgumentException(INVALID_MESSAGE_KEY);
    }
}