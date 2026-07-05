package com.myspacesante.insee.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;

/* Bloc persistance: entite BDD pour une entreprise SAP issue d'INSEE. */
@Entity
@Table(name = "sap_companies")
public class SapCompanyEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true, length = 14)
  private String siret;

  @Column(length = 9)
  private String siren;

  @Column(length = 255)
  private String denomination;

  @Column(length = 255)
  private String enseigne;

  @Column(length = 10)
  private String activitePrincipale;

  @Column(length = 255)
  private String activiteLibelle;

  @Column(length = 255)
  private String adresse;

  @Column(length = 16)
  private String codePostal;

  @Column(length = 255)
  private String ville;

  @Column(length = 50)
  private String etatAdministratif;

  @Column(nullable = false)
  private boolean matchedByNafCode;

  @Column(nullable = false)
  private boolean matchedByKeywords;

  @Column(length = 1000)
  private String matchedKeywords;

  @Column(nullable = false)
  private OffsetDateTime syncedAt;

  @Column(columnDefinition = "text")
  private String rawPayload;

  public Long getId() {
    return id;
  }

  public String getSiret() {
    return siret;
  }

  public void setSiret(String siret) {
    this.siret = siret;
  }

  public String getSiren() {
    return siren;
  }

  public void setSiren(String siren) {
    this.siren = siren;
  }

  public String getDenomination() {
    return denomination;
  }

  public void setDenomination(String denomination) {
    this.denomination = denomination;
  }

  public String getEnseigne() {
    return enseigne;
  }

  public void setEnseigne(String enseigne) {
    this.enseigne = enseigne;
  }

  public String getActivitePrincipale() {
    return activitePrincipale;
  }

  public void setActivitePrincipale(String activitePrincipale) {
    this.activitePrincipale = activitePrincipale;
  }

  public String getActiviteLibelle() {
    return activiteLibelle;
  }

  public void setActiviteLibelle(String activiteLibelle) {
    this.activiteLibelle = activiteLibelle;
  }

  public String getAdresse() {
    return adresse;
  }

  public void setAdresse(String adresse) {
    this.adresse = adresse;
  }

  public String getCodePostal() {
    return codePostal;
  }

  public void setCodePostal(String codePostal) {
    this.codePostal = codePostal;
  }

  public String getVille() {
    return ville;
  }

  public void setVille(String ville) {
    this.ville = ville;
  }

  public String getEtatAdministratif() {
    return etatAdministratif;
  }

  public void setEtatAdministratif(String etatAdministratif) {
    this.etatAdministratif = etatAdministratif;
  }

  public boolean isMatchedByNafCode() {
    return matchedByNafCode;
  }

  public void setMatchedByNafCode(boolean matchedByNafCode) {
    this.matchedByNafCode = matchedByNafCode;
  }

  public boolean isMatchedByKeywords() {
    return matchedByKeywords;
  }

  public void setMatchedByKeywords(boolean matchedByKeywords) {
    this.matchedByKeywords = matchedByKeywords;
  }

  public String getMatchedKeywords() {
    return matchedKeywords;
  }

  public void setMatchedKeywords(String matchedKeywords) {
    this.matchedKeywords = matchedKeywords;
  }

  public OffsetDateTime getSyncedAt() {
    return syncedAt;
  }

  public void setSyncedAt(OffsetDateTime syncedAt) {
    this.syncedAt = syncedAt;
  }

  public String getRawPayload() {
    return rawPayload;
  }

  public void setRawPayload(String rawPayload) {
    this.rawPayload = rawPayload;
  }
}