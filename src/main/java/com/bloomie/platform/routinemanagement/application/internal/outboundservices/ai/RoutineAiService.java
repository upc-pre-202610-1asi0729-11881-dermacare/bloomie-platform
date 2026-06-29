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
}