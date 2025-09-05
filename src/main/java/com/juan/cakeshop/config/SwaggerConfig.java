package com.juan.cakeshop.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "JWT Auth API", version = "v1"))
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        // 1) Definimos el esquema de seguridad llamado "bearerAuth"
        SecurityScheme bearerScheme = new SecurityScheme()
                .name("bearerAuth")                 // nombre lógico del esquema
                .type(SecurityScheme.Type.HTTP)     // tipo HTTP (vs. apiKey u oauth2)
                .scheme("bearer")                   // debe ser exactamente "bearer"
                .bearerFormat("JWT");               // metadato informativo (opcional)

        // 2) Lo registramos dentro de Components bajo la clave "bearerAuth"
        Components components = new Components()
                .addSecuritySchemes("bearerAuth", bearerScheme);

        // 3) Lo exigimos globalmente a todas las operaciones (botón Authorize aplica a todo)
        SecurityRequirement requirement = new SecurityRequirement()
                .addList("bearerAuth");

        // 4) Construimos el OpenAPI con el esquema y el requirement global
        return new OpenAPI()
                .components(components)
                .addSecurityItem(requirement);
    }
}
