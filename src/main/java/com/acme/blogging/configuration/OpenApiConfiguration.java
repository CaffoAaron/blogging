package com.acme.blogging.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {
    @Bean(name = "bloggingOpenApi")
    public OpenAPI bloggingOpenApi() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("Blogging Application API")
                        .description(
                                "Blogging API implemented with Spring Boot RESTful service and documented using springdoc-openapi and OpenAPI 3.0"));

    }

}
