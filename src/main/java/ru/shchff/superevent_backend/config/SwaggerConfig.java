package ru.shchff.superevent_backend.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;


@Configuration
public class SwaggerConfig
{

    @Bean
    public OpenAPI supereventOpenAPI()
    {
        return new OpenAPI()
            .servers(List.of(
                new Server()
                    .url("/")
                    .description("Base API Path")
            ))
            .info(new Info()
                .title("Superevent API")
                .description("API для бэкегда")
                .version("1.0.0")
            )
            .addSecurityItem(
                new SecurityRequirement()
                .addList("JWT")
            )
            .components(new Components()
                .addSecuritySchemes("JWT",
                    new SecurityScheme()
                        .name("JWT")
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                        .in(SecurityScheme.In.HEADER)
                )
            );
    }
}