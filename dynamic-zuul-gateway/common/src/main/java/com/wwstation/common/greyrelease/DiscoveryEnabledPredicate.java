package com.wwstation.common.greyrelease;

import com.netflix.loadbalancer.AbstractServerPredicate;
import com.netflix.loadbalancer.PredicateKey;
import com.netflix.niws.loadbalancer.DiscoveryEnabledServer;

import javax.annotation.Nullable;

/**
 * @author william
 * @description
 * @Date: 2021-06-07 10:21
 */
public abstract class DiscoveryEnabledPredicate extends AbstractServerPredicate {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean apply(@Nullable PredicateKey input) {
        return input != null
            && input.getServer() instanceof DiscoveryEnabledServer
            && apply((DiscoveryEnabledServer) input.getServer());
    }

    /**
     * Returns whether the specific {@link DiscoveryEnabledServer} matches this predicate.
     *
     * @param server the discovered server
     * @return whether the server matches the predicate
     */
    protected abstract boolean apply(DiscoveryEnabledServer server);
}
