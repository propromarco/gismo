package com.github.propromarco.gismo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * The class SwaggerConfig provides the configuration the the swagger app.
 *
 * @since 11.09.2015
 */
@Configuration
@EnableSwagger2
@ComponentScan(basePackages = "com.github.propromarco.gismo.")
public class SwaggerConfig {

    @Bean
    public Docket applicantsApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.github.propromarco.gismo."))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "backfill-management Service",
                "REST API for the backfill-management Service",
                "4.0",
                null,
                new Contact(
                        "github.com",
                        null,
                        null),
                null,
                null);
    }
}
