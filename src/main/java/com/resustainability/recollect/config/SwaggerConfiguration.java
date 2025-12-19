package com.resustainability.recollect.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

import jakarta.servlet.http.HttpServletRequest;

import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfiguration {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("ReCollect API")
                        .description("API Documentation")
                        .version("1.0.0")
                )
                .components(
                        new Components().addSecuritySchemes(
                                "bearerAuth",
                                new SecurityScheme()
                                        .name("Authorization")
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .in(SecurityScheme.In.HEADER)
                        )
                )
                .addSecurityItem(
                        new SecurityRequirement().addList("bearerAuth")
                );
    }

    @Bean
    public OpenApiCustomizer serverSchemeCustomizer(HttpServletRequest request) {
        return openApi -> {
            final String host = request.getServerName();
            final boolean isIp = host.matches("^localhost$|^\\d{1,3}(\\.\\d{1,3}){3}$");
            openApi.setServers(List.of(
                    new Server().url(isIp ? "http" : "https" + "://" + host + ":" + request.getServerPort())
            ));
        };
    }
}
