package com.bloomie.platform.skinAnalysis.domain.model.valueobjects;

import java.util.Collections;
import java.util.List;

/**
 * Value object that wraps the list of skin concerns reported by the patient.
 *
 * <p>Examples: {@code ACNE}, {@code WRINKLES}, {@code DARK_SPOTS}, {@code PORES},
 * {@code REDNESS}, {@code HYPERPIGMENTATION}.</p>
 *
 * @param concerns immutable list of concern labels; must not be null or empty
 */
public record SkinConcerns(List<String> concerns) {

    private static final String INVALID_MESSAGE_KEY = "skin_analysis.skin_concerns.invalid";

    public SkinConcerns {
        if (concerns == null || concerns.isEmpty()) {
            throw new IllegalArgumentException(INVALID_MESSAGE_KEY);
        }
        concerns = Collections.unmodifiableList(concerns);
    }
}
