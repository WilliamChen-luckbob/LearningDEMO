package com.wwstation.gateway.route.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * @author william
 * @description
 * @Date: 2021-05-28 20:36
 */
@Data
@RefreshScope
@Configuration
public class PrefixConfig {
    @Value("${zuul.prefix:}")
    private String prefix;

    @Value("${zuul.strip-prefix:true}")
    private Boolean stripPrefix;
}
