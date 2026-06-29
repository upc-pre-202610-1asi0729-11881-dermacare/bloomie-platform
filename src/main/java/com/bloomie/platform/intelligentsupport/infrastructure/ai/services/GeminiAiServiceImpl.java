package com.bloomie.platform.intelligentsupport.infrastructure.ai.services;

import com.bloomie.platform.intelligentsupport.application.internal.outboundservices.ai.AiService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * Gemini API implementation of the {@link AiService} outbound port.
 * Sends the patient's message and skin context to Gemini and returns the generated response.
 */
@Service
@Slf4j
public class GeminiAiServiceImpl implements AiService {

    private static final String SYSTEM_PROMPT =
            "You are Bloomie AI, a professional skincare assistant. " +
                    "You ONLY answer questions about skincare, skin health, " +
                    "cosmetic products, ingredients, and dermatological topics. " +
                    "If the user asks about anything unrelated to skincare, " +
                    "respond: 'I can only help with skincare-related questions. " +
                    "Please ask me about your skin, products, or routines.' " +
                    "If the question requires medical diagnosis, recommend consulting a dermatologist. " +
                    "Never make medical diagnoses. Respond in the same language as the user.";

    private static final String FALLBACK_RESPONSE =
            "I'm sorry, I couldn't process your question at this time. Please try again later.";

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public GeminiAiServiceImpl() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Calls the Gemini API with the user's message and skin context
     * and returns the AI-generated response text.
     *
     * @param userMessage the question typed by the patient
     * @param skinContext contextual information about the patient's skin profile
     * @return AI-generated response text, or fallback message on failure
     */
    @Override
    public String generateSkincareResponse(String userMessage, String skinContext) {
        try {
            var headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            var requestBody = Map.of(
                    "system_instruction", Map.of(
                            "parts", List.of(Map.of("text", SYSTEM_PROMPT))
                    ),
                    "contents", List.of(
                            Map.of(
                                    "role", "user",
                                    "parts", List.of(Map.of("text", buildPrompt(userMessage, skinContext)))
                            )
                    )
            );

            var entity = new HttpEntity<>(requestBody, headers);
            var url = apiUrl + "?key=" + apiKey;

            var response = restTemplate.postForEntity(url, entity, String.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return extractTextFromResponse(response.getBody());
            }

            log.warn("Gemini API returned status: {}", response.getStatusCode());
            return FALLBACK_RESPONSE;

        } catch (Exception e) {
            log.error("Error calling Gemini API: {}", e.getMessage());
            return FALLBACK_RESPONSE;
        }
    }

    /**
     * Builds the full prompt combining the skin context and the user's message.
     */
    private String buildPrompt(String userMessage, String skinContext) {
        return "Patient skin context: " + skinContext + "\n\nPatient question: " + userMessage;
    }

    /**
     * Extracts the response text from the Gemini API JSON response.
     */
    private String extractTextFromResponse(String responseBody) {
        try {
            JsonNode root = objectMapper.readTree(responseBody);
            return root
                    .path("candidates")
                    .get(0)
                    .path("content")
                    .path("parts")
                    .get(0)
                    .path("text")
                    .asText();
        } catch (Exception e) {
            log.error("Error parsing Gemini response: {}", e.getMessage());
            return FALLBACK_RESPONSE;
        }
    }
}