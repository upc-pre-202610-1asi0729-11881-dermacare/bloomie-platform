package com.bloomie.platform.productdiscovery.application.internal.commandservices;

import com.bloomie.platform.productdiscovery.application.commandservices.ProductCommandService;
import com.bloomie.platform.productdiscovery.application.internal.outboundservices.catalog.ProductCatalogService;
import com.bloomie.platform.productdiscovery.domain.model.aggregates.Product;
import com.bloomie.platform.productdiscovery.domain.model.commands.SeedProductsCommand;
import com.bloomie.platform.productdiscovery.domain.model.valueobjects.ProductCategory;
import com.bloomie.platform.productdiscovery.domain.repositories.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Application service that handles product catalog commands.
 * Fetches products from Open Beauty Facts via the ProductCatalogService outbound port.
 */
@Service
@Slf4j
public class ProductCommandServiceImpl implements ProductCommandService {

    private final ProductRepository    productRepository;
    private final ProductCatalogService productCatalogService;

    public ProductCommandServiceImpl(ProductRepository productRepository,
                                     ProductCatalogService productCatalogService) {
        this.productRepository    = productRepository;
        this.productCatalogService = productCatalogService;
    }

    @Override
    public void handle(SeedProductsCommand command) {
        if (productRepository.count() > 0) {
            log.info("Product catalog already seeded — skipping.");
            return;
        }
        log.info("Seeding product catalog from Open Beauty Facts...");
        seedFromOpenBeautyFacts();
    }

    private void seedFromOpenBeautyFacts() {
        var searchTerms = Map.of(
                "CLEANSER",    "face cleanser",
                "TONER",       "face toner",
                "SERUM",       "face serum",
                "MOISTURIZER", "face moisturizer",
                "SUNSCREEN",   "face sunscreen SPF"
        );

        List<Product> allProducts = new ArrayList<>();
        for (var entry : searchTerms.entrySet()) {
            var products = productCatalogService.fetchProductsByCategory(
                    entry.getValue(), entry.getKey(), 24);
            allProducts.addAll(products);
            log.info("Fetched {} products for {}", products.size(), entry.getKey());
        }

        if (!allProducts.isEmpty()) {
            productRepository.saveAll(allProducts);
            log.info("Seeded {} products from Open Beauty Facts.", allProducts.size());
        } else {
            log.warn("Open Beauty Facts returned nothing — falling back to default catalog.");
            productRepository.saveAll(buildInitialCatalog());
        }
    }

    private List<Product> buildInitialCatalog() {
        return List.of(
                buildProduct("CeraVe Foaming Facial Cleanser", "CeraVe", ProductCategory.CLEANSER,
                        "A gentle foaming cleanser for normal to oily skin.",
                        List.of("Removes excess oil", "Maintains skin barrier", "Non-comedogenic"), true),
                buildProduct("La Roche-Posay Toleriane Hydrating Gentle Cleanser", "La Roche-Posay", ProductCategory.CLEANSER,
                        "A hydrating gentle cleanser for sensitive and dry skin.",
                        List.of("Hydrates while cleansing", "Suitable for sensitive skin", "Fragrance-free"), false),
                buildProduct("Paula's Choice Skin Balancing Pore-Reducing Toner", "Paula's Choice", ProductCategory.TONER,
                        "A lightweight toner that minimizes pores and balances oil production.",
                        List.of("Minimizes pores", "Balances oil production", "Brightens skin"), true),
                buildProduct("The Ordinary Hyaluronic Acid 2% + B5", "The Ordinary", ProductCategory.SERUM,
                        "A hydration serum that supports healthy skin moisture levels.",
                        List.of("Deep hydration", "Plumps skin", "Lightweight"), true),
                buildProduct("Cetaphil Moisturizing Lotion", "Cetaphil", ProductCategory.MOISTURIZER,
                        "A fast-absorbing moisturizer for all skin types.",
                        List.of("Long-lasting hydration", "Non-greasy formula", "Gentle"), true),
                buildProduct("EltaMD UV Clear Broad-Spectrum SPF 46", "EltaMD", ProductCategory.SUNSCREEN,
                        "A lightweight sunscreen that calms and protects sensitive skin.",
                        List.of("SPF 46", "Oil-free formula", "Calms acne-prone skin"), true)
        );
    }

    private Product buildProduct(String name, String brand, ProductCategory category,
                                 String description, List<String> benefits, boolean aiRecommended) {
        var product = new Product();
        product.setName(name);
        product.setBrand(brand);
        product.setCategory(category);
        product.setDescription(description);
        product.setBenefits(benefits);
        product.setAiRecommended(aiRecommended);
        product.setImageUrl(fallbackImageFor(category));
        return product;
    }

    private String fallbackImageFor(ProductCategory category) {
        return switch (category) {
            case CLEANSER    -> "https://images.unsplash.com/photo-1556228578-8c89e6adf883?w=400&q=80";
            case TONER       -> "https://images.unsplash.com/photo-1620916566398-39f1143ab7be?w=400&q=80";
            case SERUM       -> "https://images.unsplash.com/photo-1617897903246-719242758050?w=400&q=80";
            case MOISTURIZER -> "https://images.unsplash.com/photo-1611080626919-7cf5a9dbab12?w=400&q=80";
            case SUNSCREEN   -> "https://images.unsplash.com/photo-1556228720-195a672e8a03?w=400&q=80";
        };
    }
}