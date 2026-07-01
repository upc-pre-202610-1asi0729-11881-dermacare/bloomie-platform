package com.bloomie.platform.routinemanagement.infrastructure.ai.services;

import com.bloomie.platform.routinemanagement.application.internal.outboundservices.ai.RoutineAiService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
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

    /** Parsed once from {@link #PRODUCT_CATALOG} so catalog membership can be checked without re-parsing text. */
    private static final Map<String, List<String>> CATALOG_BY_STEP = parseCatalog(PRODUCT_CATALOG);

    private static Map<String, List<String>> parseCatalog(String catalog) {
        var result = new HashMap<String, List<String>>();
        for (String line : catalog.strip().split("\n")) {
            var parts = line.split(":", 2);
            if (parts.length != 2) continue;
            var products = Arrays.stream(parts[1].split(","))
                    .map(String::trim)
                    .filter(name -> !name.isEmpty())
                    .toList();
            result.put(parts[0].trim(), products);
        }
        return result;
    }

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

            text = text.replaceAll("```json|```", "").trim();

            return objectMapper.readValue(text, Map.class);
        } catch (Exception e) {
            log.error("Error parsing Gemini routine response: {}", e.getMessage());
            return fallbackProducts(skinType, steps);
        }
    }

    @Override
    public List<String> getCatalogProductsForStep(String step) {
        return CATALOG_BY_STEP.getOrDefault(step, List.of());
    }

    @Override
    public List<String> selectAlternativeProductsForStep(String skinType, String step, String currentProduct) {
        try {
            var prompt = buildAlternativesPrompt(skinType, step, currentProduct);
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
                return parseAlternativesResponse(response.getBody(), step, currentProduct);
            }
        } catch (Exception e) {
            log.error("Error calling Gemini for product alternatives: {}", e.getMessage());
        }

        return fallbackAlternatives(step, currentProduct);
    }

    private String buildAlternativesPrompt(String skinType, String step, String currentProduct) {
        return String.format("""
                You are a skincare expert. Suggest alternative products for a patient with %s skin
                who currently uses "%s" for their %s step.

                Available products for this step:
                %s

                Respond ONLY with a JSON array of exact product names from the list above,
                excluding the current product, like:
                ["exact product name", "exact product name"]
                Do not include any explanation or markdown, only the JSON array.
                """, skinType, currentProduct, step, String.join(", ", getCatalogProductsForStep(step)));
    }

    @SuppressWarnings("unchecked")
    private List<String> parseAlternativesResponse(String responseBody, String step, String currentProduct) {
        try {
            var root = objectMapper.readTree(responseBody);
            var text = root.path("candidates").get(0)
                    .path("content").path("parts").get(0)
                    .path("text").asText();

            // limpia posibles backticks de markdown
            text = text.replaceAll("```json|```", "").trim();

            return objectMapper.readValue(text, List.class);
        } catch (Exception e) {
            log.error("Error parsing Gemini alternatives response: {}", e.getMessage());
            return fallbackAlternatives(step, currentProduct);
        }
    }

    private List<String> fallbackAlternatives(String step, String currentProduct) {
        return getCatalogProductsForStep(step).stream()
                .filter(product -> !product.equals(currentProduct))
                .toList();
    }

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