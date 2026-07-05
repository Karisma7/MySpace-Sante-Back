package com.myspacesante.insee.config;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/* Bloc config: proprietes centralisees pour l'acces a l'API INSEE. */
@Validated
@ConfigurationProperties(prefix = "app.insee")
public record InseeApiProperties(
    @NotBlank String baseUrl,
    String apiKey,
    @Min(1) int pageSize,
    @NotBlank String searchEndpoint
) {
}