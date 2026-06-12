// GetAllPlansQuery.java
package com.bloomie.platform.subscription.domain.model.queries;

public record GetAllPlansQuery(String type) {
    public GetAllPlansQuery {
        if (type == null || type.isBlank())
            throw new IllegalArgumentException("Type filter cannot be null or blank");
    }
}