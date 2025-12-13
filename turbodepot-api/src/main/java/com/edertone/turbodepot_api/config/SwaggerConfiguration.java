package com.edertone.turbodepot_api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger configuration.
 */
@OpenAPIDefinition(
    info = @Info(title = "TurboDepot API", version = "1.0"),
    security = @SecurityRequirement(name = "bearer-token")
)
@SecurityScheme(
    name = "bearer-token",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "token",
    scheme = "bearer"
)
@Configuration
public class SwaggerConfiguration {
}
