package com.bloomie.platform.intelligentsupport.application.internal.outboundservices.ai;

/**
 * Outbound port for AI-powered skincare response generation.
 * The infrastructure layer provides the concrete Gemini implementation.
 */
public interface AiService {

    /**
     * Generates a skincare-focused response from the AI model.
     *
     * @param userMessage the question typed by the patient
     * @param skinContext contextual information about the patient's skin profile
     * @return AI-generated response text
     */
    String generateSkincareResponse(String userMessage, String skinContext);
}