package com.bloomie.platform.payments.application.internal.outboundservices.acl;

import com.bloomie.platform.subscription.interfaces.acl.SubscriptionContextFacade;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Anti-corruption layer that translates queries from the Payments BC into calls
 * to the {@link SubscriptionContextFacade} published language of the Subscription BC.
 */
@Service
public class ExternalSubscriptionService {
    private final SubscriptionContextFacade subscriptionContextFacade;

    /**
     * Constructor
     *
     * @param subscriptionContextFacade The {@link SubscriptionContextFacade} instance
     */
    public ExternalSubscriptionService(SubscriptionContextFacade subscriptionContextFacade) {
        this.subscriptionContextFacade = subscriptionContextFacade;
    }

    /**
     * Checks whether a plan with the given id exists in the Subscription BC.
     *
     * @param planId The plan identifier to look up
     * @return An {@link Optional} containing the plan id if found, otherwise empty
     */
    public Optional<Long> fetchPlanById(Long planId) {
        boolean exists = subscriptionContextFacade.existsPlanById(planId);
        return exists ? Optional.of(planId) : Optional.empty();
    }

    /**
     * Fetches the price of a plan from the Subscription BC.
     *
     * @param planId The plan identifier to look up
     * @return An {@link Optional} containing the plan price if found, otherwise empty
     */
    public Optional<Double> fetchPlanPrice(Long planId) {
        var price = subscriptionContextFacade.fetchPlanPrice(planId);
        return price == null ? Optional.empty() : Optional.of(price);
    }
}
