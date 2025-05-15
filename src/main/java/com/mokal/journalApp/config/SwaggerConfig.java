package com.mokal.journalApp.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;

import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {    @Bean
    public OpenAPI myCustomeConfig(){
        return new OpenAPI()
        .info(
                new Info()
                    .title("Journal App API's")
                    .description("@Mokal2002")
                    .version("1.0")
        )
        .servers(List.of(
                new Server()
                    .url("http://localhost:8080/home")
                    .description("local"),
                new Server()
                    .url("https://journal-app-mokal2002.herokuapp.com/home")
                    .description("Heroku"))
        )
        .tags(List.of(
            new Tag()
                .name("Public API's")
                .description("Endpoints that don't require authentication")
                .extensions(Map.of("x-order", 1)),
            new Tag()
                .name("User API's")
                .description("User management endpoints")
                .extensions(Map.of("x-order", 2)),
            new Tag()
                .name("Journal API's")
                .description("Journal entry management endpoints")
                .extensions(Map.of("x-order", 3)),
            new Tag()
                .name("Admin API's")
                .description("Administrative endpoints")
                .extensions(Map.of("x-order", 4))

        ))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components().addSecuritySchemes(
                        "bearerAuth", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER)
                                .name("Authorization")
                ));
    }
}
