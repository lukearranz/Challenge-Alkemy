package com.challenge.alkemy.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiDetails())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }


    private ApiInfo apiDetails() {
        return new ApiInfo("ALKEMY SPRINGBOOT CHALLENGE - API REST",
                "Api Rest docs for the alkemy challenge",
                "1.0.0",
                "http://www.lucasarranz.com.ar",
                new Contact("Lucas Arranz Garcia", "http://www.lucasarranz.com.ar", "lucas.arranz@hotmail.com"),
                "TODOS LOS DERECHOS RESERVADOS",
                "http://www.lucasarranz.com.ar",
                Collections.emptyList());
    }


}
