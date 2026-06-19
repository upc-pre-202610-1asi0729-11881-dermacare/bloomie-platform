package com.bloomie.platform.productdiscovery.application.internal.commandservices;

import com.bloomie.platform.productdiscovery.application.commandservices.ProductCommandService;
import com.bloomie.platform.productdiscovery.domain.model.aggregates.Product;
import com.bloomie.platform.productdiscovery.domain.model.commands.SeedProductsCommand;
import com.bloomie.platform.productdiscovery.domain.model.valueobjects.ProductCategory;
import com.bloomie.platform.productdiscovery.domain.repositories.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Application service that handles product catalog commands.
 */
@Service
@Slf4j
public class ProductCommandServiceImpl implements ProductCommandService {

    private final ProductRepository productRepository;

    public ProductCommandServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void handle(SeedProductsCommand command) {
        if (productRepository.count() > 0) {
            log.info("Product catalog already seeded — skipping.");
            return;
        }
        log.info("Seeding initial product catalog...");
        productRepository.saveAll(buildInitialCatalog());
        log.info("Product catalog seeding complete.");
    }

    private List<Product> buildInitialCatalog() {
        return List.of(
                buildProduct("CeraVe Foaming Facial Cleanser", "CeraVe", ProductCategory.CLEANSER,
                        "A gentle foaming cleanser for normal to oily skin that removes excess oil without disrupting the skin barrier.",
                        List.of("Removes excess oil", "Maintains skin barrier", "Non-comedogenic"), true),
                buildProduct("La Roche-Posay Toleriane Hydrating Gentle Cleanser", "La Roche-Posay", ProductCategory.CLEANSER,
                        "A hydrating gentle cleanser for sensitive and dry skin that soothes and protects.",
                        List.of("Hydrates while cleansing", "Suitable for sensitive skin", "Fragrance-free"), false),
                buildProduct("Neutrogena Ultra Gentle Daily Cleanser", "Neutrogena", ProductCategory.CLEANSER,
                        "A daily gentle cleanser for all skin types formulated with minimal ingredients.",
                        List.of("Fragrance-free", "Hypoallergenic", "Soap-free"), false),

                buildProduct("Paula's Choice Skin Balancing Pore-Reducing Toner", "Paula's Choice", ProductCategory.TONER,
                        "A lightweight toner that minimizes pores and balances oil production for combination and oily skin.",
                        List.of("Minimizes pores", "Balances oil production", "Brightens skin"), true),
                buildProduct("Thayers Witch Hazel Alcohol-Free Toner", "Thayers", ProductCategory.TONER,
                        "An alcohol-free toner with witch hazel and aloe vera that soothes and refreshes all skin types.",
                        List.of("Soothes skin", "Alcohol-free", "Refreshes complexion"), false),

                buildProduct("The Ordinary Hyaluronic Acid 2% + B5", "The Ordinary", ProductCategory.SERUM,
                        "A hydration serum that supports healthy skin moisture levels and plumps dry skin.",
                        List.of("Deep hydration", "Plumps skin", "Lightweight"), true),
                buildProduct("SkinCeuticals C E Ferulic", "SkinCeuticals", ProductCategory.SERUM,
                        "A vitamin C serum that provides environmental protection and brightening benefits.",
                        List.of("Brightens skin", "Antioxidant protection", "Reduces fine lines"), true),
                buildProduct("Paula's Choice 10% Niacinamide Booster", "Paula's Choice", ProductCategory.SERUM,
                        "A concentrated serum that visibly reduces enlarged pores and evens skin tone.",
                        List.of("Reduces pore size", "Evens skin tone", "Reduces redness"), false),

                buildProduct("Cetaphil Moisturizing Lotion", "Cetaphil", ProductCategory.MOISTURIZER,
                        "A fast-absorbing, non-greasy moisturizer for all skin types that provides long-lasting hydration.",
                        List.of("Long-lasting hydration", "Non-greasy formula", "Gentle for daily use"), true),
                buildProduct("First Aid Beauty Ultra Repair Cream", "First Aid Beauty", ProductCategory.MOISTURIZER,
                        "An intense moisturizer for dry and sensitive skin with colloidal oatmeal and shea butter.",
                        List.of("Intensive repair", "Soothes irritation", "Rich barrier cream"), false),
                buildProduct("Neutrogena Hydro Boost Water Gel", "Neutrogena", ProductCategory.MOISTURIZER,
                        "An oil-free water gel moisturizer with hyaluronic acid designed for normal to oily skin.",
                        List.of("Oil-free", "Intense hydration", "Lightweight gel texture"), true),

                buildProduct("EltaMD UV Clear Broad-Spectrum SPF 46", "EltaMD", ProductCategory.SUNSCREEN,
                        "A lightweight, oil-free sunscreen that calms and protects sensitive skin prone to breakouts.",
                        List.of("SPF 46 broad-spectrum", "Oil-free formula", "Calms acne-prone skin"), true),
                buildProduct("Supergoop! Unseen Sunscreen SPF 40", "Supergoop!", ProductCategory.SUNSCREEN,
                        "An invisible, weightless sunscreen that doubles as a makeup primer for all skin tones.",
                        List.of("Invisible finish", "Makeup-friendly primer", "SPF 40 broad-spectrum"), false),
                buildProduct("La Roche-Posay Anthelios Mineral SPF 50", "La Roche-Posay", ProductCategory.SUNSCREEN,
                        "A mineral sunscreen ideal for sensitive skin with a soothing matte finish.",
                        List.of("Mineral formula", "Sensitive skin safe", "SPF 50 broad-spectrum"), true)
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
        return product;
    }
}
