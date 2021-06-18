package com.wwstation.gateway.route.config;

import com.infiai.webgateway.route.NewZuulRouteLocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RefreshScope
public class NewZuulConfig {
	@Autowired
	private PrefixConfig prefixConfig;

	@Autowired
	private ZuulProperties zuulProperties;

	@Autowired
	private ServerProperties serverProperties;

	@Bean
	public NewZuulRouteLocator routeLocator() {

		NewZuulRouteLocator routeLocator = new NewZuulRouteLocator(
			prefixConfig.getPrefix()
			, this.zuulProperties);

		return routeLocator;
	}
}
