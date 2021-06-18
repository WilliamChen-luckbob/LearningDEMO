package com.wwstation.common.greyrelease;

import com.netflix.loadbalancer.AbstractServerPredicate;
import com.netflix.loadbalancer.AvailabilityPredicate;
import com.netflix.loadbalancer.CompositePredicate;
import com.netflix.loadbalancer.PredicateBasedRule;
import org.springframework.util.Assert;

/**
 * @author william
 * @description
 * @Date: 2021-06-07 10:20
 */
public abstract class DiscoveryEnabledRule extends PredicateBasedRule {

    private final CompositePredicate predicate;

    /**
     * Creates new instance of {@link DiscoveryEnabledRule} class with specific predicate.
     *
     * @param discoveryEnabledPredicate the discovery enabled predicate, can't be null
     * @throws IllegalArgumentException if {@code discoveryEnabledPredicate} is {@code null}
     */
    public DiscoveryEnabledRule(DiscoveryEnabledPredicate discoveryEnabledPredicate) {
        Assert.notNull(discoveryEnabledPredicate, "Parameter 'discoveryEnabledPredicate' can't be null");
        this.predicate = createCompositePredicate(discoveryEnabledPredicate, new AvailabilityPredicate(this, null));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AbstractServerPredicate getPredicate() {
        return predicate;
    }

    /**
     * Creates the composite predicate with fallback strategies.
     *
     * @param discoveryEnabledPredicate the discovery service predicate
     * @param availabilityPredicate     the availability predicate
     * @return the composite predicate
     */
    private CompositePredicate createCompositePredicate(DiscoveryEnabledPredicate discoveryEnabledPredicate, AvailabilityPredicate availabilityPredicate) {
        return CompositePredicate.withPredicates(discoveryEnabledPredicate, availabilityPredicate)
            .build();
    }
}
