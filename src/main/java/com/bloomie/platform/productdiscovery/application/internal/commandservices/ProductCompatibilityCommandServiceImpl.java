package com.bloomie.platform.productdiscovery.application.internal.commandservices;

import com.bloomie.platform.productdiscovery.application.commandservices.ProductCompatibilityCommandService;
import com.bloomie.platform.productdiscovery.domain.model.aggregates.ProductCompatibility;
import com.bloomie.platform.productdiscovery.domain.model.commands.GenerateProductCompatibilityCommand;
import com.bloomie.platform.productdiscovery.domain.repositories.ProductCompatibilityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Application service that handles {@link GenerateProductCompatibilityCommand}.
 *
 * <p>Evaluates compatibility using deterministic rules based on the product's
 * category and the target skin type, then persists the result via the
 * {@link ProductCompatibilityRepository} domain port.</p>
 */
@Service
@Slf4j
public class ProductCompatibilityCommandServiceImpl implements ProductCompatibilityCommandService {

    private final ProductCompatibilityRepository productCompatibilityRepository;

    /**
     * Constructs the service with its required dependencies.
     *
     * @param productCompatibilityRepository domain repository port for persistence
     */
    public ProductCompatibilityCommandServiceImpl(ProductCompatibilityRepository productCompatibilityRepository) {
        this.productCompatibilityRepository = productCompatibilityRepository;
    }

    /**
     * Generates a rule-based compatibility score for the given product and skin type,
     * then persists the result as a {@link ProductCompatibility} aggregate.
     *
     * @param command the command containing product details and target skin type
     */
    @Override
    public void handle(GenerateProductCompatibilityCommand command) {
        log.info("Generating compatibility for product '{}' and skin type '{}'",
                command.productName(), command.skinType());

        var score = calculateScore(command.category(), command.skinType());
        var reason = buildReason(command.skinType());

        var compatibility = new ProductCompatibility(
                command.productId(),
                command.skinType(),
                score,
                reason
        );

        productCompatibilityRepository.save(compatibility);

        log.info("Saved compatibility for product {} / {}: score={}",
                command.productId(), command.skinType(), score);
    }

    /**
     * Calculates a compatibility score based on known category-skin-type affinities.
     */
    private int calculateScore(String category, String skinType) {
        return switch (category.toUpperCase()) {
            case "CLEANSER" -> switch (skinType) {
                case "OILY" -> 90;
                case "DRY" -> 70;
                case "SENSITIVE" -> 75;
                case "COMBINATION" -> 85;
                default -> 80;
            };
            case "MOISTURIZER" -> switch (skinType) {
                case "DRY" -> 95;
                case "OILY" -> 60;
                case "SENSITIVE" -> 80;
                case "COMBINATION" -> 75;
                default -> 85;
            };
            case "SERUM" -> switch (skinType) {
                case "OILY" -> 85;
                case "DRY" -> 80;
                case "SENSITIVE" -> 70;
                case "COMBINATION" -> 82;
                default -> 80;
            };
            case "SUNSCREEN" -> switch (skinType) {
                case "SENSITIVE" -> 85;
                case "DRY" -> 88;
                default -> 90;
            };
            case "TONER" -> switch (skinType) {
                case "OILY" -> 88;
                case "DRY" -> 65;
                case "SENSITIVE" -> 60;
                case "COMBINATION" -> 82;
                default -> 78;
            };
            default -> 75;
        };
    }

    /**
     * Builds a human-readable reason based on the target skin type.
     */
    private String buildReason(String skinType) {
        return switch (skinType) {
            case "OILY" -> "Helps control excess oil and maintain a balanced complexion.";
            case "DRY" -> "Provides essential moisture and nourishment for dry skin.";
            case "SENSITIVE" -> "Formulated to minimize irritation for sensitive skin.";
            case "COMBINATION" -> "Balances oily and dry areas for combination skin.";
            default -> "Suitable for maintaining healthy and balanced skin.";
        };
    }
}
