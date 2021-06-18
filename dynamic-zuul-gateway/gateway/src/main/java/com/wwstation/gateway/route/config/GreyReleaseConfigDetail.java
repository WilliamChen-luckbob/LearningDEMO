package com.wwstation.gateway.route.config;

import lombok.Data;

/**
 * @author william
 * @description
 * @Date: 2021-05-31 19:07
 */
@Data
public class GreyReleaseConfigDetail {

    //请求方法
    private String method;
    //所在服务的名称
    private String serviceName;
    //请求url
    private String url;
    //LB占比，最低0，最高9，调至9 OK后直接全部发布
    private Integer percentage;
    //负载均衡tag
//    private String greyReleaseTag;
}
