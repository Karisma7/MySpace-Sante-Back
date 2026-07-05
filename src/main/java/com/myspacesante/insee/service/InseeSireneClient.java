package com.myspacesante.insee.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.myspacesante.insee.config.InseeApiProperties;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

/* Bloc integration: client resilient face a la reponse JSON Sirene. */
@Component
public class InseeSireneClient {

  private final RestClient restClient;
  private final InseeApiProperties properties;

  public InseeSireneClient(RestClient inseeRestClient, InseeApiProperties properties) {
    this.restClient = inseeRestClient;
    this.properties = properties;
  }

  public List<JsonNode> fetchActiveSapEstablishments(Set<String> nafCodes, int maxPages) {
    List<JsonNode> establishments = new ArrayList<>();
    String cursor = "*";

    for (int pageIndex = 0; pageIndex < maxPages; pageIndex += 1) {
      JsonNode payload = restClient.get()
          .uri(buildSearchUri(cursor, nafCodes))
          .retrieve()
          .body(JsonNode.class);

      if (payload == null) {
        break;
      }

      JsonNode rows = payload.path("etablissements");
      if (rows.isArray()) {
        rows.forEach(establishments::add);
      }

      String nextCursor = payload.path("header").path("curseurSuivant").asText();
      if (!StringUtils.hasText(nextCursor) || nextCursor.equals(cursor)) {
        break;
      }

      cursor = nextCursor;
    }

    return establishments;
  }

  /* Bloc requete: construire un filtre OR sur les codes NAF SAP actifs. */
  private String buildSearchUri(String cursor, Set<String> nafCodes) {
    String query = nafCodes.stream()
        .sorted()
      .map(code -> "periode(activitePrincipaleEtablissement:" + code + ")")
        .reduce((left, right) -> left + " OR " + right)
      .map(expression -> "(" + expression + ") AND periode(etatAdministratifEtablissement:A)")
      .orElse("periode(etatAdministratifEtablissement:A)");

    return UriComponentsBuilder.fromPath(properties.searchEndpoint())
        .queryParam("q", query)
        .queryParam("nombre", properties.pageSize())
        .queryParam("curseur", cursor)
        .build()
        .toUriString();
  }
}