package com.affectflux.api;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI affectFluxOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("AffectFlux API")
                .version("v1"));
    }
}
