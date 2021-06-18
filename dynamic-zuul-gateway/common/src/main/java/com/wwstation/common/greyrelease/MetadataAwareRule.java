package com.wwstation.common.greyrelease;

import com.google.common.base.Optional;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import com.netflix.niws.loadbalancer.DiscoveryEnabledServer;
import com.wwstation.common.Constants;
import com.wwstation.common.config.RibbonFilterContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author william
 * @description
 * @Date: 2021-06-07 10:19
 */
@Slf4j
public class MetadataAwareRule extends DiscoveryEnabledRule {

    /**
     * Creates new instance of {@link MetadataAwareRule}.
     */
    public MetadataAwareRule() {
        this(new MetadataAwarePredicate());
    }

    /**
     * Creates new instance of {@link MetadataAwareRule} with specific predicate.
     *
     * @param predicate the predicate, can't be {@code null}
     * @throws IllegalArgumentException if predicate is {@code null}
     */
    public MetadataAwareRule(DiscoveryEnabledPredicate predicate) {
        super(predicate);
    }


    @Override
    public Server choose(Object key) {
        ILoadBalancer lb = getLoadBalancer();
        String greyTag = RibbonFilterContextHolder.getCurrentContext().get(Constants.ATTRIBUTE_GREY_RELEASE_LB_KEY);

        List<Server> allServers = lb.getAllServers();

        //与标签相匹配的结果
        List<Server> availableServers = new ArrayList<>();
        //不代标签的结果
        List<Server> stableServers = new ArrayList<>();

        for (Server server : allServers) {
            String metadata = ((DiscoveryEnabledServer) server).getInstanceInfo().getMetadata().get(Constants.ATTRIBUTE_GREY_RELEASE_LB_KEY);
            //装入稳定的版本
            if (StringUtils.isEmpty(metadata)) {
                stableServers.add(server);
            }
            //装入匹配的版本
            if (StringUtils.isEmpty(greyTag) && StringUtils.isEmpty(metadata)) {
                availableServers.add(server);
                continue;
            }
            if (org.apache.commons.lang3.StringUtils.isNotEmpty(greyTag) && greyTag.equalsIgnoreCase(metadata) ) {
                availableServers.add(server);
                continue;
            }
        }

        //如果匹配不到，默认尝试从稳定版里面选择服务，如果还是没有，将返回LB异常
        if (CollectionUtils.isEmpty(availableServers)) {
            if (CollectionUtils.isEmpty(stableServers)) {
                return null;
            }
            availableServers = stableServers;
        }

        Optional<Server> server = getPredicate().chooseRoundRobinAfterFiltering(availableServers, key);
        log.debug("灰度发布tag={}，所有稳定实例为{}，可用实例为{}",greyTag,stableServers,availableServers);
        if (server.isPresent()) {
            return server.get();
        } else {
            return null;
        }
    }

}
