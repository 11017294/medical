package com.chen.medical.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * <p>
 *  swagger配置类
 * </p>
 *
 * @author MaybeBin
 * @since 2022-01-25 09:25
 */
@Configuration
@EnableSwagger2WebMvc
public class Knife4jConfiguration {

    @Bean
    public Docket webApiConfig() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(webApiInfo())
                //分组名称
                .groupName("webApi")
                .select()
                .paths(PathSelectors.regex("/api/.*"))
                .build();
        return docket;
    }

    @Bean
    public Docket adminApiConfig() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(webApiInfo())
                //分组名称
                .groupName("adminApi")
                .select()
                .paths(PathSelectors.regex("/admin/.*"))
                .build();
        return docket;
    }

    private ApiInfo webApiInfo(){
        return new ApiInfoBuilder()
                .title("尚医通APIs")
                .description("尚医通APIs")
                .version("1.0")
                .build();
    }
}
