package com.myspacesante.insee.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClient;

/* Bloc config: client HTTP avec entetes INSEE prepares une seule fois. */
@Configuration
public class RestClientConfig {

  @Bean
  RestClient inseeRestClient(InseeApiProperties properties) {
    RestClient.Builder builder = RestClient.builder()
        .baseUrl(properties.baseUrl())
        .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

    if (StringUtils.hasText(properties.apiKey())) {
      builder.defaultHeader("X-INSEE-Api-Key-Integration", properties.apiKey());
    }

    return builder.build();
  }
}