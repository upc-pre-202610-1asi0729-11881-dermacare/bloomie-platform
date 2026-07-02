package com.bloomie.platform.productdiscovery.infrastructure.openbeautyfacts.services;

import com.bloomie.platform.productdiscovery.application.internal.outboundservices.catalog.ProductCatalogService;
import com.bloomie.platform.productdiscovery.domain.model.aggregates.Product;
import com.bloomie.platform.productdiscovery.domain.model.valueobjects.ProductCategory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Open Beauty Facts implementation of {@link ProductCatalogService}.
 * Fetches skincare products with images from the Open Beauty Facts public API.
 */
@Service
@Slf4j
public class OpenBeautyFactsCatalogServiceImpl implements ProductCatalogService {

    private static final String OPEN_BEAUTY_FACTS_URL =
            "https://world.openbeautyfacts.org/cgi/search.pl";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public OpenBeautyFactsCatalogServiceImpl() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public List<Product> fetchProductsByCategory(String searchTerm, String category, int limit) {
        try {
            var url = OPEN_BEAUTY_FACTS_URL
                    + "?search_terms=" + searchTerm.replace(" ", "+")
                    + "&json=true"
                    + "&page_size=" + limit
                    + "&fields=product_name,brands,generic_name,image_front_url,image_url,image_small_url,ingredients_text";

            var headers = new HttpHeaders();
            headers.set("User-Agent", "Bloomie/1.0 (academic project)");
            var entity   = new HttpEntity<>(headers);
            var response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return parseProducts(response.getBody(), category);
            }
        } catch (Exception e) {
            log.warn("Failed to fetch products for category {}: {}", category, e.getMessage());
        }
        return List.of();
    }

    private List<Product> parseProducts(String responseBody, String category) {
        List<Product> products = new ArrayList<>();
        try {
            JsonNode root           = objectMapper.readTree(responseBody);
            JsonNode items          = root.path("products");
            var      productCategory = ProductCategory.valueOf(category);

            for (JsonNode item : items) {
                var name  = item.path("product_name").asText("").trim();
                var brand = item.path("brands").asText("Unknown").trim();

                if (name.isBlank() || name.length() < 3) continue;
                if (!name.matches("[\\x00-\\x7F\\s\\p{Punct}]+")) continue;

                var lowerName = name.toLowerCase();
                if (lowerName.contains("lip") || lowerName.contains("baby") ||
                        lowerName.contains("body") || lowerName.contains("hand")) continue;

                var imageUrl = item.path("image_front_url").asText("");
                if (imageUrl.isBlank()) imageUrl = item.path("image_url").asText("");
                if (imageUrl.isBlank()) imageUrl = item.path("image_small_url").asText("");
                if (imageUrl.isBlank()) imageUrl = getFallbackImage(category);

                var genericName = item.path("generic_name").asText("").trim();
                String description;
                if (!genericName.isBlank()) {
                    description = genericName.length() > 200
                            ? genericName.substring(0, 200) + "..."
                            : genericName;
                } else {
                    description = "A " + brand.split(",")[0].trim() + " " +
                            category.toLowerCase() + " product designed for daily skincare routine.";
                }

                if (!imageUrl.startsWith("https://images.openbeautyfacts.org")) continue;

                if (lowerName.contains("without") || lowerName.contains("with essential")) continue;

                if (name.contains("(") || name.contains("With Esse") || name.contains("Without Esse")) continue;

                var benefits = extractBenefitsFromDescription(description, category);

                var product = new Product();
                product.setName(name.length() > 100 ? name.substring(0, 100) : name);
                product.setBrand(brand.split(",")[0].trim());
                product.setCategory(productCategory);
                product.setDescription(description);
                product.setBenefits(benefits);
                product.setAiRecommended(false);
                product.setImageUrl(imageUrl);
                products.add(product);
            }
        } catch (Exception e) {
            log.error("Error parsing Open Beauty Facts response: {}", e.getMessage());
        }
        return products;
    }

    private List<String> extractBenefitsFromDescription(String description, String category) {
        var lower = description.toLowerCase();
        List<String> benefits = new ArrayList<>();
        
        if (lower.contains("hydrat") || lower.contains("moistur")) benefits.add("Hydrating formula");
        if (lower.contains("bright") || lower.contains("glow"))    benefits.add("Brightening effect");
        if (lower.contains("anti-ag") || lower.contains("aging"))  benefits.add("Anti-aging properties");
        if (lower.contains("sensitiv"))                             benefits.add("Suitable for sensitive skin");
        if (lower.contains("oil-free") || lower.contains("non-greasy")) benefits.add("Oil-free formula");
        if (lower.contains("spf") || lower.contains("sunscreen"))  benefits.add("Sun protection");
        if (lower.contains("vitamin c") || lower.contains("ascorbic")) benefits.add("Vitamin C enriched");
        if (lower.contains("hyaluronic"))                           benefits.add("Hyaluronic acid");
        if (lower.contains("niacinamide"))                          benefits.add("Niacinamide formula");
        if (lower.contains("gentle") || lower.contains("mild"))    benefits.add("Gentle cleansing");
        if (lower.contains("pore"))                                 benefits.add("Pore minimizing");
        if (lower.contains("repair") || lower.contains("barrier")) benefits.add("Barrier repair");

        if (benefits.isEmpty()) {
            return getDefaultBenefits(category);
        }
        
        return benefits.subList(0, Math.min(benefits.size(), 3));
    }

    private List<String> getDefaultBenefits(String category) {
        return switch (category) {
            case "CLEANSER"    -> List.of("Cleanses skin", "Removes impurities", "Gentle formula");
            case "TONER"       -> List.of("Balances pH", "Hydrates skin", "Prepares for serums");
            case "SERUM"       -> List.of("Targets skin concerns", "Concentrated formula", "Fast-absorbing");
            case "MOISTURIZER" -> List.of("Hydrates skin", "Strengthens barrier", "Daily moisture");
            case "SUNSCREEN"   -> List.of("UV protection", "Prevents damage", "Daily protection");
            default            -> List.of("Skincare benefits");
        };
    }

    private String getFallbackImage(String category) {
        return switch (category) {
            case "CLEANSER"    -> "https://images.unsplash.com/photo-1556228578-8c89e6adf883?w=400&q=80";
            case "TONER"       -> "https://images.unsplash.com/photo-1620916566398-39f1143ab7be?w=400&q=80";
            case "SERUM"       -> "https://images.unsplash.com/photo-1617897903246-719242758050?w=400&q=80";
            case "MOISTURIZER" -> "https://images.unsplash.com/photo-1611080626919-7cf5a9dbab12?w=400&q=80";
            case "SUNSCREEN"   -> "https://images.unsplash.com/photo-1556228720-195a672e8a03?w=400&q=80";
            default            -> "https://images.unsplash.com/photo-1611080626919-7cf5a9dbab12?w=400&q=80";
        };
    }
}