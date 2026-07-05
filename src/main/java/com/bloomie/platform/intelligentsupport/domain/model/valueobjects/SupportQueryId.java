package com.bloomie.platform.intelligentsupport.domain.model.valueobjects;

public record SupportQueryId(Long supportQueryId) {
    private static final String INVALID_MESSAGE_KEY = "intelligent.support.query.id.invalid";
    public SupportQueryId {
        if (supportQueryId == null || supportQueryId < 1)
            throw new IllegalArgumentException(INVALID_MESSAGE_KEY);
    }
}