package com.wwstation.common.greyrelease;

import com.netflix.niws.loadbalancer.DiscoveryEnabledServer;

/**
 * @author william
 * @description
 * @Date: 2021-06-07 10:20
 */
public class MetadataAwarePredicate extends DiscoveryEnabledPredicate {

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean apply(DiscoveryEnabledServer server) {
        //自定义的getEligibleServers数据源已经是筛过的了，下方不再要求判定，直接返回true,让它轮询
        return true;
//        final RibbonFilterContext context = RibbonFilterContextHolder.getCurrentContext();
//        final Set<Map.Entry<String, String>> attributes = Collections.unmodifiableSet(context.getAttributes().entrySet());
//        final Map<String, String> metadata = server.getInstanceInfo().getMetadata();
//        return metadata.entrySet().containsAll(attributes);
    }
}