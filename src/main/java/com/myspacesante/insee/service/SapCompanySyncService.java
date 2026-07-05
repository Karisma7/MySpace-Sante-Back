package com.myspacesante.insee.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myspacesante.insee.model.SapCompanyEntity;
import com.myspacesante.insee.repository.SapCompanyRepository;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/* Bloc service: orchestration complete entre INSEE, filtrage et BDD. */
@Service
public class SapCompanySyncService {

  private final InseeSireneClient inseeSireneClient;
  private final ServiceAPersonneRules serviceAPersonneRules;
  private final SapCompanyRepository repository;
  private final ObjectMapper objectMapper;

  public SapCompanySyncService(
      InseeSireneClient inseeSireneClient,
      ServiceAPersonneRules serviceAPersonneRules,
      SapCompanyRepository repository,
      ObjectMapper objectMapper
  ) {
    this.inseeSireneClient = inseeSireneClient;
    this.serviceAPersonneRules = serviceAPersonneRules;
    this.repository = repository;
    this.objectMapper = objectMapper;
  }

  @Transactional
  public SyncReport synchronize(int maxPages) {
    List<JsonNode> fetchedRows = inseeSireneClient.fetchActiveSapEstablishments(serviceAPersonneRules.queryNafCodes(), maxPages);
    int inspected = fetchedRows.size();
    int persisted = 0;

    for (JsonNode establishment : fetchedRows) {
      if (upsertIfMatched(establishment)) {
        persisted += 1;
      }
    }

    return new SyncReport(inspected, persisted);
  }

  public List<SapCompanyEntity> list(int limit) {
    return repository.findAllByOrderByDenominationAsc(org.springframework.data.domain.PageRequest.of(0, limit));
  }

  /* Bloc mapping: ignorer les lignes hors SAP avant la persistence. */
  private boolean upsertIfMatched(JsonNode establishment) {
    String siret = text(establishment, "siret");
    if (siret == null || siret.isBlank()) {
      return false;
    }

    JsonNode uniteLegale = establishment.path("uniteLegale");
    String nafCode = firstNonBlank(
        text(establishment, "activitePrincipaleEtablissement"),
        text(uniteLegale, "activitePrincipaleUniteLegale")
    );
    String activityLabel = firstNonBlank(
        text(establishment, "libelleActivitePrincipaleEtablissement"),
        text(uniteLegale, "libelleActivitePrincipaleUniteLegale")
    );
    String denomination = firstNonBlank(
        text(uniteLegale, "denominationUniteLegale"),
        text(uniteLegale, "denominationUsuelle1UniteLegale"),
        text(establishment, "enseigne1Etablissement")
    );
    String enseigne = firstNonBlank(
        text(establishment, "enseigne1Etablissement"),
        text(establishment, "nomCommercialEtablissement")
    );

    ServiceAPersonneRules.MatchResult match = serviceAPersonneRules.match(nafCode, activityLabel, denomination, enseigne);
    if (!match.byNafCode() && !match.byKeywords()) {
      return false;
    }

    SapCompanyEntity entity = repository.findBySiret(siret).orElseGet(SapCompanyEntity::new);
    entity.setSiret(siret);
    entity.setSiren(text(establishment, "siren"));
    entity.setDenomination(denomination);
    entity.setEnseigne(enseigne);
    entity.setActivitePrincipale(nafCode);
    entity.setActiviteLibelle(activityLabel);
    entity.setCategorieJuridique(text(uniteLegale, "categorieJuridiqueUniteLegale"));
    entity.setTrancheEffectifs(firstNonBlank(
      text(establishment, "trancheEffectifsEtablissement"),
      text(uniteLegale, "trancheEffectifsUniteLegale")
    ));
    entity.setStatutDiffusionEtablissement(text(establishment, "statutDiffusionEtablissement"));
    entity.setActivitePrincipaleNaf25(firstNonBlank(
      text(establishment, "activitePrincipaleNAF25Etablissement"),
      text(uniteLegale, "activitePrincipaleNAF25UniteLegale")
    ));
    entity.setActivitePrincipaleRegistreMetiers(text(establishment, "activitePrincipaleRegistreMetiersEtablissement"));
    entity.setAdresseComplement(text(establishment.path("adresseEtablissement"), "complementAdresseEtablissement"));
    entity.setAdresseNumeroVoie(text(establishment.path("adresseEtablissement"), "numeroVoieEtablissement"));
    entity.setAdresseIndiceRepetition(text(establishment.path("adresseEtablissement"), "indiceRepetitionEtablissement"));
    entity.setAdresseTypeVoie(text(establishment.path("adresseEtablissement"), "typeVoieEtablissement"));
    entity.setAdresseLibelleVoie(text(establishment.path("adresseEtablissement"), "libelleVoieEtablissement"));
    entity.setCodePostal(text(establishment, "codePostalEtablissement"));
    entity.setVille(text(establishment.path("adresseEtablissement"), "libelleCommuneEtablissement"));
    entity.setCodeCommune(text(establishment.path("adresseEtablissement"), "codeCommuneEtablissement"));
    entity.setAdresseIdentifiant(text(establishment.path("adresseEtablissement"), "identifiantAdresseEtablissement"));
    entity.setDateCreationEtablissement(text(establishment, "dateCreationEtablissement"));
    entity.setDateDernierTraitementEtablissement(text(establishment, "dateDernierTraitementEtablissement"));
    entity.setEtatAdministratif(text(establishment, "etatAdministratifEtablissement"));
    entity.setMatchedByNafCode(match.byNafCode());
    entity.setMatchedByKeywords(match.byKeywords());
    entity.setMatchedKeywords(new java.util.ArrayList<>(match.matchedKeywords()));
    entity.setSyncedAt(OffsetDateTime.now());
    entity.setRawPayload(serialize(establishment));
    repository.save(entity);
    return true;
  }

  private String serialize(JsonNode node) {
    try {
      return objectMapper.writeValueAsString(node);
    } catch (JsonProcessingException exception) {
      return node.toString();
    }
  }

  private String text(JsonNode node, String fieldName) {
    JsonNode value = node.path(fieldName);
    return value.isMissingNode() || value.isNull() ? null : value.asText(null);
  }

  private String firstNonBlank(String... values) {
    for (String value : values) {
      if (value != null && !value.isBlank()) {
        return value;
      }
    }

    return null;
  }

  private String joinNonBlank(String... values) {
    return java.util.Arrays.stream(values)
        .filter(value -> value != null && !value.isBlank())
        .reduce((left, right) -> left + " " + right)
        .orElse(null);
  }

  /* Bloc resultat: retour de synchronisation minimal pour l'API REST. */
  public record SyncReport(int inspected, int persisted) {
  }
}