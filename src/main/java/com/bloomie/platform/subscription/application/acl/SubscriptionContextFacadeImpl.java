package com.bloomie.platform.subscription.application.acl;

import com.bloomie.platform.subscription.application.commandservices.SubscriptionCommandService;
import com.bloomie.platform.subscription.application.queryservices.SubscriptionQueryService;
import com.bloomie.platform.subscription.domain.model.entities.Plan;
import com.bloomie.platform.subscription.domain.model.queries.GetPlanByIdQuery;
import com.bloomie.platform.subscription.interfaces.acl.SubscriptionContextFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Implementation of the {@link SubscriptionContextFacade} published language interface.
 *
 * <p>Delegates to the internal Subscription BC query and command services,
 * shielding other bounded contexts from the internal application layer.</p>
 */
@Service
@Slf4j
public class SubscriptionContextFacadeImpl implements SubscriptionContextFacade {
    private final SubscriptionCommandService subscriptionCommandService;
    private final SubscriptionQueryService subscriptionQueryService;

    /**
     * Constructor
     *
     * @param subscriptionCommandService The {@link SubscriptionCommandService} instance
     * @param subscriptionQueryService   The {@link SubscriptionQueryService} instance
     */
    public SubscriptionContextFacadeImpl(SubscriptionCommandService subscriptionCommandService, SubscriptionQueryService subscriptionQueryService) {
        this.subscriptionCommandService = subscriptionCommandService;
        this.subscriptionQueryService = subscriptionQueryService;
    }

    // inherited javadoc
    @Override
    public boolean existsPlanById(Long id) {
        var query = new GetPlanByIdQuery(id);
        return subscriptionQueryService.handle(query).isPresent();
    }

    // inherited javadoc
    @Override
    public Double fetchPlanPrice(Long id) {
        var query = new GetPlanByIdQuery(id);
        return subscriptionQueryService.handle(query).map(Plan::getPrice).orElse(null);
    }
}
