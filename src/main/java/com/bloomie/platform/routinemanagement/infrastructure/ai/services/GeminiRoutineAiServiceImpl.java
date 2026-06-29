package com.bloomie.platform.routinemanagement.infrastructure.ai.services;

import com.bloomie.platform.routinemanagement.application.internal.outboundservices.ai.RoutineAiService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Gemini implementation of {@link RoutineAiService}.
 * Selects the best product for each routine step based on skin type.
 */
@Service
@Slf4j
public class GeminiRoutineAiServiceImpl implements RoutineAiService {

    private static final String PRODUCT_CATALOG = """
            CLEANSER: CeraVe Foaming Facial Cleanser, La Roche-Posay Toleriane Hydrating Gentle Cleanser, Neutrogena Ultra Gentle Daily Cleanser
            TONER: Paula's Choice Skin Balancing Pore-Reducing Toner, Thayers Witch Hazel Alcohol-Free Toner
            SERUM: The Ordinary Hyaluronic Acid 2% + B5, SkinCeuticals C E Ferulic, Paula's Choice 10% Niacinamide Booster
            MOISTURIZER: Cetaphil Moisturizing Lotion, First Aid Beauty Ultra Repair Cream, Neutrogena Hydro Boost Water Gel
            SUNSCREEN: EltaMD UV Clear Broad-Spectrum SPF 46, Supergoop! Unseen Sunscreen SPF 40, La Roche-Posay Anthelios Mineral SPF 50
            """;

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public GeminiRoutineAiServiceImpl() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Map<String, String> selectProductsForRoutine(String skinType, List<String> steps) {
        try {
            var prompt = buildPrompt(skinType, steps);
            var headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            var requestBody = Map.of(
                    "contents", List.of(
                            Map.of("role", "user",
                                    "parts", List.of(Map.of("text", prompt)))
                    )
            );

            var entity   = new HttpEntity<>(requestBody, headers);
            var url      = apiUrl + "?key=" + apiKey;
            var response = restTemplate.postForEntity(url, entity, String.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return parseResponse(response.getBody(), steps, skinType);
            }
        } catch (Exception e) {
            log.error("Error calling Gemini for routine generation: {}", e.getMessage());
        }

        return fallbackProducts(skinType, steps);
    }

    private String buildPrompt(String skinType, List<String> steps) {
        return String.format("""
                You are a skincare expert. Select the best product for each step for a patient with %s skin.
                
                Available products:
                %s
                
                Steps needed: %s
                
                Respond ONLY with a JSON object like:
                {"CLEANSER": "exact product name", "MOISTURIZER": "exact product name", ...}
                Include only the steps listed. Use exact product names from the list above.
                Do not include any explanation or markdown, only the JSON.
                """, skinType, PRODUCT_CATALOG, steps);
    }

    @SuppressWarnings("unchecked")
    private Map<String, String> parseResponse(String responseBody, List<String> steps, String skinType) {
        try {
            var root = objectMapper.readTree(responseBody);
            var text = root.path("candidates").get(0)
                    .path("content").path("parts").get(0)
                    .path("text").asText();

            // limpia posibles backticks de markdown
            text = text.replaceAll("```json|```", "").trim();

            return objectMapper.readValue(text, Map.class);
        } catch (Exception e) {
            log.error("Error parsing Gemini routine response: {}", e.getMessage());
            return fallbackProducts(skinType, steps);
        }
    }

    /**
     * Fallback determinístico si Gemini falla.
     */
    private Map<String, String> fallbackProducts(String skinType, List<String> steps) {
        var defaults = new HashMap<String, String>();
        for (String step : steps) {
            defaults.put(step, switch (step) {
                case "CLEANSER"    -> "OILY".equals(skinType)
                        ? "CeraVe Foaming Facial Cleanser"
                        : "La Roche-Posay Toleriane Hydrating Gentle Cleanser";
                case "TONER"       -> "Paula's Choice Skin Balancing Pore-Reducing Toner";
                case "SERUM"       -> "The Ordinary Hyaluronic Acid 2% + B5";
                case "MOISTURIZER" -> "OILY".equals(skinType)
                        ? "Neutrogena Hydro Boost Water Gel"
                        : "Cetaphil Moisturizing Lotion";
                case "SUNSCREEN"   -> "EltaMD UV Clear Broad-Spectrum SPF 46";
                default            -> step + " product";
            });
        }
        return defaults;
    }
}