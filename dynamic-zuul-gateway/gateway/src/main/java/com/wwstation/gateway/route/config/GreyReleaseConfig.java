package com.wwstation.gateway.route.config;

import cn.hutool.core.collection.CollectionUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 用于灰度发布
 * @author william
 * @description
 * @Date: 2021-05-31 18:03
 */
@Configuration
@RefreshScope
@ConfigurationProperties(prefix = "grey-urls")
@Data
public class GreyReleaseConfig {
    private List<GreyReleaseConfigDetail> urls;

    private Map<String,GreyReleaseConfigDetail> urlMap;

    //缺省默认不加Test标签
    @Value("${zuul.greyPercentage:0}")
    private Integer globalGreyPercentage;

    @PostConstruct
    private void init(){
        if (CollectionUtil.isNotEmpty(urls)){
           urlMap= urls.stream().collect(Collectors.toMap(
                k->k.getMethod()+"|/"+k.getServiceName()+ k.getUrl(),
                v->v
            ));
        }
    }
}
