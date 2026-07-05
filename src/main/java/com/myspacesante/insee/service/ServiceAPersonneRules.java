package com.myspacesante.insee.service;

import java.text.Normalizer;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import org.springframework.stereotype.Component;

/* Bloc metier: regles centralisees de qualification services a la personne. */
@Component
public class ServiceAPersonneRules {

  private static final Set<String> NAF_CODES = Set.of(
      "8121Z",
      "8130Z",
      "8810A",
      "8810B",
      "8891A",
      "8891B",
      "8899B",
      "9602A",
      "9604Z",
      "9609Z"
  );

  private static final List<String> KEYWORDS = List.of(
      "aide a domicile",
      "service a domicile",
      "services a la personne",
      "aide a la personne",
      "auxiliaire de vie",
      "garde d enfants",
      "garde denfants",
      "assistance aux personnes agees",
      "assistance aux personnes handicapees",
      "menage",
      "nettoyage",
      "jardinage",
      "portage de repas",
      "livraison de repas",
      "soutien scolaire",
      "coiffure a domicile",
      "soins esthetiques a domicile",
      "teleassistance",
      "petits travaux"
  );

  public Set<String> nafCodes() {
    return NAF_CODES;
  }

  public Set<String> queryNafCodes() {
    return NAF_CODES.stream()
        .map(this::formatForQuery)
        .collect(java.util.stream.Collectors.toUnmodifiableSet());
  }

  public MatchResult match(String nafCode, String... texts) {
    String normalizedCode = normalizeNafCode(nafCode);
    String normalizedText = normalize(String.join(" ", texts));
    List<String> matchedKeywords = KEYWORDS.stream()
        .filter(keyword -> normalizedText.contains(normalize(keyword)))
        .toList();

    return new MatchResult(
        NAF_CODES.contains(normalizedCode),
        !matchedKeywords.isEmpty(),
        matchedKeywords
    );
  }

  /* Bloc utilitaire: normaliser les textes pour un matching tolerant. */
  private String normalize(String value) {
    return Normalizer.normalize(value == null ? "" : value, Normalizer.Form.NFD)
        .replaceAll("\\p{M}", "")
        .toLowerCase(Locale.ROOT)
        .trim();
  }

  /* Bloc utilitaire: comparer les codes NAF sans tenir compte des points ou espaces. */
  private String normalizeNafCode(String value) {
    return normalize(value).replaceAll("[^a-z0-9]", "").toUpperCase(Locale.ROOT);
  }

  /* Bloc utilitaire: produire le format attendu par la recherche Sirene, ex. 88.10A. */
  private String formatForQuery(String code) {
    String normalizedCode = normalizeNafCode(code);
    if (normalizedCode.length() == 5 && Character.isDigit(normalizedCode.charAt(0)) && Character.isDigit(normalizedCode.charAt(1))) {
      return normalizedCode.substring(0, 2) + "." + normalizedCode.substring(2);
    }

    return normalizedCode;
  }

  /* Bloc valeur: encapsuler le resultat de filtrage pour reuse simple. */
  public record MatchResult(boolean byNafCode, boolean byKeywords, List<String> matchedKeywords) {
  }
}