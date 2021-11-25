package com.wwstation.apidoc.swagger;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author william
 * @description
 * @Date: 2021-01-28 16:38
 */
@EnableOpenApi
@Configuration
@ConditionalOnProperty(prefix = "swagger",name = "host")
@ConditionalOnExpression("${swagger.enable:true}")
public class SwaggerConfig {

    private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES =
        new HashSet<String>(Arrays.asList("application/json"));
    @Value("${swagger.host}")
    private String host;
    @Value("${swagger.enable}")
    private Boolean enable;

    /**
     * 创建API应用
     * apiInfo() 增加API相关信息
     * 通过select()函数返回一个ApiSelectorBuilder实例,用来控制哪些接口暴露给Swagger来展现，
     * 本例采用指定扫描的包路径来定义指定要建立API的目录。
     *
     * @return
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30)
            .enable(enable)
            .apiInfo(apiInfo())
            .consumes(DEFAULT_PRODUCES_AND_CONSUMES)
            .produces(DEFAULT_PRODUCES_AND_CONSUMES)
//            .operationOrdering((o1, o2) -> {
//                if (o1.getPosition() > o2.getPosition()) {
//                    return 1;
//                }
//                return -1;
//            })
//            .apiListingReferenceOrdering((o1, o2) -> {
//                if (o1.getPosition() > o2.getPosition()) {
//                    return 1;
//                }
//                return -1;
//            })
            .host("http://localhost:")
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.wwstation"))
            //只显示加了注解的api
            .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
            .paths(PathSelectors.any())
            .build();
    }

    /**
     * 创建该API的基本信息（这些基本信息会展现在文档页面中）
     * 访问地址：http://项目实际地址/swagger-ui.html
     *
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("测试文档")
            .contact(new Contact("william", "18777399984", "william.chen@wwstation.com"))
            .description("wwstation数据系统后台测试文档")
            .version("1.0")
            .build();
    }
}
