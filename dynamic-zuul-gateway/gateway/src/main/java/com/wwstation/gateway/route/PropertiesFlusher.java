package com.wwstation.gateway.route;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import com.google.common.collect.Lists;
import com.wwstation.gateway.route.entity.ZuulRouteEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties.ZuulRoute;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 在配置进行刷新或者心跳时会触发@refreshscope事件或刷新拉取事件，导致此类被调用，刷新数据
 * 从nacos配置中心主动拉取数据
 */
@Component
@Slf4j
public class PropertiesFlusher {
	@Value("${spring.cloud.nacos.config.server-addr:}")
	private String nacosAddr;
	@Value("${spring.cloud.nacos.config.namespace:}")
	private String nacosNamespace;
	private ConfigService configService;

	public Map<String, ZuulRoute> getProperties() {
		Map<String, ZuulRoute> routes = new LinkedHashMap<>();
		List<ZuulRouteEntity> results = getRouteFromNacos("main-gateway-dynamic-route", "dynamic_route");
		for (ZuulRouteEntity result : results) {
			if (StringUtils.isBlank(result.getUrl())&&StringUtils.isBlank(result.getServiceId())){
				log.error("配置失败！serviceId和Url必须配置一个！但不能都配置！");
				continue;
			}
			if (StringUtils.isBlank(result.getPath())
				/*|| org.apache.commons.lang3.StringUtils.isBlank(result.getUrl())*/) {
				continue;
			}
			if (!result.getEnabled()) {
				log.info("路由路径{}被禁用，跳过!", result.getPath());
				continue;
			}
			ZuulRoute zuulRoute = new ZuulRoute();
			try {
				BeanUtils.copyProperties(result, zuulRoute);
			} catch (Exception e) {
			}
			routes.put(zuulRoute.getPath(), zuulRoute);
		}
		return routes;
	}

	private <T> T getFromNacos(String dataId, String group, Class<T> clz) {
		try {
			if (StringUtils.isNotEmpty(nacosAddr) && StringUtils.isNotEmpty(nacosNamespace) && configService == null) {
				Properties properties = new Properties();
				properties.put(PropertyKeyConst.SERVER_ADDR, nacosAddr);
				properties.put(PropertyKeyConst.NAMESPACE, nacosNamespace);
				this.configService = NacosFactory.createConfigService(properties);
			}

			String content = configService.getConfig(dataId, group, 5000);
			log.debug("从Nacos返回的配置：{}", content);
			return JSONObject.parseObject(content, clz);
		} catch (NacosException e) {
			e.printStackTrace();
		}
		return null;
	}

	private <T> List<T> getListFromNacos(String dataId, String group, Class<T> clz) {
		try {
			if (StringUtils.isNotEmpty(nacosAddr) && StringUtils.isNotEmpty(nacosNamespace) && configService == null) {
				Properties properties = new Properties();
				properties.put(PropertyKeyConst.SERVER_ADDR, nacosAddr);
				properties.put(PropertyKeyConst.NAMESPACE, nacosNamespace);
				this.configService = NacosFactory.createConfigService(properties);
			}
			String content = configService.getConfig(dataId, group, 5000);
			log.debug("从Nacos返回的配置：{}", content);
			return JSONObject.parseArray(content, clz);
		} catch (NacosException e) {
			e.printStackTrace();
		}
		return Lists.newArrayList();
	}

	private List<ZuulRouteEntity> getRouteFromNacos(String dataId, String group) {
		return getListFromNacos(dataId, group, ZuulRouteEntity.class);
	}

}