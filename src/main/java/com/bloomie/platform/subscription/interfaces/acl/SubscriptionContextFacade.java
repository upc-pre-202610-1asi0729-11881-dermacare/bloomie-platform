package com.bloomie.platform.subscription.interfaces.acl;

/**
 * Published language facade for the Subscription bounded context.
 *
 * <p>Other bounded contexts must use this interface to query subscription data,
 * never coupling directly to the Subscription BC's internal application services.</p>
 */
public interface SubscriptionContextFacade {
    /**
     * Checks whether a plan with the given id exists.
     *
     * @param id The plan identifier
     * @return {@code true} if the plan exists, {@code false} otherwise
     */
    boolean existsPlanById(Long id);

    /**
     * Fetches the price of a plan by its id.
     *
     * @param planId The plan identifier
     * @return The plan price, or {@code null} if the plan does not exist
     */
    Double fetchPlanPrice(Long planId);
}
