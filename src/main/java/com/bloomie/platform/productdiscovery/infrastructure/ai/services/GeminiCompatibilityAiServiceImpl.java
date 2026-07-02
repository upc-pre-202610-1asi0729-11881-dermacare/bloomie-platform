package com.bloomie.platform.productdiscovery.infrastructure.ai.services;

import com.bloomie.platform.productdiscovery.application.internal.outboundservices.compatibility.ProductCompatibilityAiService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * Gemini API implementation of the {@link ProductCompatibilityAiService} outbound port.
 *
 * <p>Sends the product name, category and skin type to Gemini and parses
 * the returned JSON into a {@link ProductCompatibilityResult}.
 * Falls back to category-aware default scores if the API call fails.</p>
 */
@Service
@Slf4j
public class GeminiCompatibilityAiServiceImpl implements ProductCompatibilityAiService {

    private static final String PROMPT_TEMPLATE =
            "You are a skincare expert. Evaluate how compatible this product is for a specific skin type.\n\n" +
            "Product: %s\n" +
            "Category: %s\n" +
            "Skin type: %s\n\n" +
            "Respond ONLY with a JSON object like:\n" +
            "{\"score\": 85, \"reason\": \"Gentle formula suitable for oily skin without clogging pores\"}\n\n" +
            "Score must be 0-100. Reason must be one sentence under 100 characters.\n" +
            "Do not include markdown, backticks or any explanation. Only the JSON.";

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    /**
     * Constructs the service, creating the HTTP client and JSON mapper.
     */
    public GeminiCompatibilityAiServiceImpl() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Calls the Gemini API to evaluate the compatibility between a product and a skin type.
     *
     * @param productName the display name of the product
     * @param category    the skincare category (e.g. CLEANSER, SERUM)
     * @param skinType    the target skin type (e.g. OILY, DRY, SENSITIVE)
     * @return the AI-generated score and reason, or a category-based fallback on failure
     */
    @Override
    public ProductCompatibilityResult evaluateCompatibility(String productName, String category, String skinType) {
        try {
            var headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            var prompt = PROMPT_TEMPLATE.formatted(productName, category, skinType);

            var requestBody = Map.of(
                    "contents", List.of(
                            Map.of(
                                    "role", "user",
                                    "parts", List.of(Map.of("text", prompt))
                            )
                    )
            );

            var entity = new HttpEntity<>(requestBody, headers);
            var url = apiUrl + "?key=" + apiKey;

            var response = restTemplate.postForEntity(url, entity, String.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return parseCompatibilityResult(response.getBody(), category, skinType);
            }

            log.warn("Gemini API returned status: {}", response.getStatusCode());
            return buildFallbackResult(category, skinType);

        } catch (Exception e) {
            log.error("Error calling Gemini API for compatibility evaluation: {}", e.getMessage());
            return buildFallbackResult(category, skinType);
        }
    }

    /**
     * Extracts the JSON text from Gemini's response envelope and parses score and reason.
     */
    private ProductCompatibilityResult parseCompatibilityResult(String responseBody, String category, String skinType) {
        try {
            JsonNode root = objectMapper.readTree(responseBody);
            String jsonText = root
                    .path("candidates")
                    .get(0)
                    .path("content")
                    .path("parts")
                    .get(0)
                    .path("text")
                    .asText();

            JsonNode result = objectMapper.readTree(jsonText.trim());
            int score = result.path("score").asInt();
            String reason = result.path("reason").asText();

            if (score < 0 || score > 100 || reason.isBlank()) {
                log.warn("Gemini returned invalid compatibility data — using fallback");
                return buildFallbackResult(category, skinType);
            }

            return new ProductCompatibilityResult(score, reason);

        } catch (Exception e) {
            log.error("Error parsing Gemini compatibility response: {}", e.getMessage());
            return buildFallbackResult(category, skinType);
        }
    }

    /**
     * Builds a reasonable default result based on known category–skin-type affinities
     * when the AI service is unavailable or returns unparseable data.
     */
    private ProductCompatibilityResult buildFallbackResult(String category, String skinType) {
        int score = switch (category) {
            case "CLEANSER" -> switch (skinType) {
                case "OILY"        -> 80;
                case "DRY"         -> 60;
                case "SENSITIVE"   -> 50;
                case "COMBINATION" -> 75;
                default            -> 70;
            };
            case "TONER" -> switch (skinType) {
                case "OILY"        -> 75;
                case "DRY"         -> 55;
                case "SENSITIVE"   -> 45;
                case "COMBINATION" -> 70;
                default            -> 65;
            };
            case "SERUM" -> switch (skinType) {
                case "OILY"        -> 65;
                case "DRY"         -> 80;
                case "SENSITIVE"   -> 60;
                case "COMBINATION" -> 75;
                default            -> 70;
            };
            case "MOISTURIZER" -> switch (skinType) {
                case "OILY"        -> 55;
                case "DRY"         -> 85;
                case "SENSITIVE"   -> 70;
                case "COMBINATION" -> 65;
                default            -> 75;
            };
            case "SUNSCREEN" -> 85;
            default          -> 65;
        };

        return new ProductCompatibilityResult(score, "General compatibility based on product category and skin type.");
    }
}