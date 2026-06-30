package com.bloomie.platform.subscription.application.internal.commandservices;

import com.bloomie.platform.subscription.application.commandservices.PlanCommandService;
import com.bloomie.platform.subscription.domain.model.commands.SeedPlansCommand;
import com.bloomie.platform.subscription.domain.model.entities.Plan;
import com.bloomie.platform.subscription.domain.model.valueobjects.PlanType;
import com.bloomie.platform.subscription.domain.repositories.PlanRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of {@link PlanCommandService}.
 * Seeds the initial plan catalog on application startup if no plans exist.
 */
@Service
public class PlanCommandServiceImpl implements PlanCommandService {

    private static final Logger log = LoggerFactory.getLogger(PlanCommandServiceImpl.class);

    private final PlanRepository planRepository;

    public PlanCommandServiceImpl(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    /**
     * Seeds the initial plan catalog if no plans exist.
     *
     * @param command the seed plans command
     */
    @Override
    public void handle(SeedPlansCommand command) {
        if (planRepository.count() > 0) {
            log.info("Plan catalog already seeded — skipping.");
            return;
        }

        log.info("Seeding initial plan catalog...");

        var plans = buildInitialCatalog();
        plans.forEach(planRepository::save);

        log.info("Plan catalog seeding complete — {} plans created.", plans.size());
    }

    /**
     * Builds the initial set of subscription plans.
     *
     * @return list of plans to seed
     */
    private List<Plan> buildInitialCatalog() {
        return List.of(
                new Plan(null, PlanType.STARTER, "Starter",
                        9.99, 30,
                        List.of("AI Skin Analysis", "Basic Routine", "Product Discovery")),
                new Plan(null, PlanType.ADVANCED, "Advanced",
                        19.99, 30,
                        List.of("AI Skin Analysis", "Personalized Routine",
                                "Product Discovery", "Dermatologist Appointments",
                                "AI Chat Support")),
                new Plan(null, PlanType.ELITE, "Elite",
                        39.99, 30,
                        List.of("Unlimited AI Skin Analysis", "AI-Powered Routine",
                                "Product Discovery", "Priority Dermatologist Appointments",
                                "24/7 AI Chat Support", "Video Consultations"))
        );
    }
}