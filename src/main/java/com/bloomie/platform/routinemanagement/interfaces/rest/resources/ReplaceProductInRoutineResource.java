package com.bloomie.platform.routinemanagement.interfaces.rest.resources;

/**
 * Resource for requesting a product replacement in a routine item.
 *
 * @param newProductRecommendation the product name to replace the current recommendation with
 */
public record ReplaceProductInRoutineResource(String newProductRecommendation) {
}
