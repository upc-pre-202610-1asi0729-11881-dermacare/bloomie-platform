package com.bloomie.platform.productdiscovery.domain.model.queries;

/**
 * Query to retrieve all compatibility evaluations for a specific skin type.
 *
 * @param skinType the target skin type (e.g. OILY, DRY, SENSITIVE, COMBINATION, NORMAL)
 */
public record GetCompatibilitiesBySkinTypeQuery(String skinType) {}
