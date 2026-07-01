package com.bloomie.platform.routinemanagement.application.internal.outboundservices.ai;

import java.util.Map;

/**
 * Outbound port for AI-powered skincare routine product selection.
 */
public interface RoutineAiService {

    /**
     * Selects the best product for each routine step based on the patient's skin type.
     *
     * @param skinType the patient's skin type (OILY, DRY, SENSITIVE, COMBINATION, NORMAL)
     * @param steps    the list of steps in the routine (CLEANSER, MOISTURIZER, etc.)
     * @return a map of step → selected product name
     */
    Map<String, String> selectProductsForRoutine(String skinType, java.util.List<String> steps);

    /**
     * Selects alternative product recommendations for a single routine step,
     * excluding the product currently assigned, based on the patient's skin type.
     *
     * @param skinType       the patient's skin type (OILY, DRY, SENSITIVE, COMBINATION, NORMAL)
     * @param step           the routine step to find alternatives for (CLEANSER, TONER, etc.)
     * @param currentProduct the product currently recommended for this step, excluded from the results
     * @return alternative product names from the product catalog for that step
     */
    java.util.List<String> selectAlternativeProductsForStep(String skinType, String step, String currentProduct);

    /**
     * Returns every catalog product available for a given routine step.
     * Used to validate that a chosen replacement is a legitimate, recognized product.
     *
     * @param step the routine step (CLEANSER, TONER, etc.)
     * @return the catalog product names for that step, or an empty list if the step is unknown
     */
    java.util.List<String> getCatalogProductsForStep(String step);
}