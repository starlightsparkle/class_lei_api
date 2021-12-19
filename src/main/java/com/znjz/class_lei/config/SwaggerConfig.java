package com.znjz.class_lei.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.HashSet;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private String apiName = "JWT";
    private String headerKey = "authorization";
    private HashSet<String> protocolSet = new HashSet<>();

    @Bean
    public Docket createRestApi() {
        protocolSet.add("https");
        protocolSet.add("http");
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.znjz.class_lei.controller"))
                .paths(PathSelectors.any())
                .build()
                .protocols(protocolSet)
                .useDefaultResponseMessages(false)
                ;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("雷课堂APIs文档")
                .description("更多请关注http://www.baidu.com")
                .version("1.0")
                .build();
    }


}
