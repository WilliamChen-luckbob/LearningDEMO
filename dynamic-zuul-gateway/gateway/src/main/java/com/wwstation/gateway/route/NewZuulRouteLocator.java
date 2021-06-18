package com.wwstation.gateway.route;

import com.wwstation.gateway.route.config.PrefixConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.RefreshableRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.SimpleRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties.ZuulRoute;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

public class NewZuulRouteLocator extends SimpleRouteLocator implements RefreshableRouteLocator {
	@Autowired
	private PrefixConfig prefixConfig;
	@Autowired
	private ZuulProperties properties;

	@Autowired
	private PropertiesFlusher propertiesFlusher;

	public NewZuulRouteLocator(String servletPath, ZuulProperties properties) {
		super(servletPath, properties);
		this.properties = properties;
	}

	@Override
	public void refresh() {
		doRefresh();
	}

	@Override
	protected Map<String, ZuulRoute> locateRoutes() {
		properties.setPrefix(prefixConfig.getPrefix());
		properties.setStripPrefix(prefixConfig.getStripPrefix());

		LinkedHashMap<String, ZuulRoute> routesMap = new LinkedHashMap<String, ZuulRoute>();
		// 从application.properties中加载路由信息
		routesMap.putAll(super.locateRoutes());
		// 从Nacos中加载路由信息
		routesMap.putAll(propertiesFlusher.getProperties());
		// 优化一下配置
		LinkedHashMap<String, ZuulRoute> values = new LinkedHashMap<>();
		for (Map.Entry<String, ZuulRoute> entry : routesMap.entrySet()) {
			String path = entry.getKey();
			// Prepend with slash if not already present.
			if (!path.startsWith("/")) {
				path = "/" + path;
			}
			if (StringUtils.hasText(this.properties.getPrefix()) && !this.properties.getPrefix().equalsIgnoreCase("/")) {
				path = this.properties.getPrefix() + path;
				if (!path.startsWith("/")) {
					path = "/" + path;
				}
			}
			values.put(path, entry.getValue());
		}
		return values;
	}
}
