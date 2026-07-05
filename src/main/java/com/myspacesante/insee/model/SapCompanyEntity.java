package com.myspacesante.insee.model;

import jakarta.persistence.Column;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Entity;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.OrderColumn;
import java.util.ArrayList;
import java.util.List;
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
  private String categorieJuridique;

  @Column(length = 50)
  private String trancheEffectifs;

  @Column(length = 50)
  private String statutDiffusionEtablissement;

  @Column(length = 10)
  private String activitePrincipaleNaf25;

  @Column(length = 10)
  private String activitePrincipaleRegistreMetiers;

  @Column(length = 255)
  private String adresseComplement;

  @Column(length = 20)
  private String adresseNumeroVoie;

  @Column(length = 20)
  private String adresseIndiceRepetition;

  @Column(length = 50)
  private String adresseTypeVoie;

  @Column(length = 255)
  private String adresseLibelleVoie;

  @Column(length = 16)
  private String codePostal;

  @Column(length = 255)
  private String ville;

  @Column(length = 20)
  private String codeCommune;

  @Column(length = 255)
  private String adresseIdentifiant;

  @Column(length = 50)
  private String dateCreationEtablissement;

  @Column(length = 50)
  private String dateDernierTraitementEtablissement;

  @Column(length = 50)
  private String etatAdministratif;

  @Column(nullable = false)
  private boolean matchedByNafCode;

  @Column(nullable = false)
  private boolean matchedByKeywords;

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "sap_company_matched_keywords", joinColumns = @JoinColumn(name = "sap_company_id"))
  @OrderColumn(name = "keyword_order")
  @Column(name = "keyword", length = 255)
  private List<String> matchedKeywords = new ArrayList<>();

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

  public String getCategorieJuridique() {
    return categorieJuridique;
  }

  public void setCategorieJuridique(String categorieJuridique) {
    this.categorieJuridique = categorieJuridique;
  }

  public String getTrancheEffectifs() {
    return trancheEffectifs;
  }

  public void setTrancheEffectifs(String trancheEffectifs) {
    this.trancheEffectifs = trancheEffectifs;
  }

  public String getStatutDiffusionEtablissement() {
    return statutDiffusionEtablissement;
  }

  public void setStatutDiffusionEtablissement(String statutDiffusionEtablissement) {
    this.statutDiffusionEtablissement = statutDiffusionEtablissement;
  }

  public String getActivitePrincipaleNaf25() {
    return activitePrincipaleNaf25;
  }

  public void setActivitePrincipaleNaf25(String activitePrincipaleNaf25) {
    this.activitePrincipaleNaf25 = activitePrincipaleNaf25;
  }

  public String getActivitePrincipaleRegistreMetiers() {
    return activitePrincipaleRegistreMetiers;
  }

  public void setActivitePrincipaleRegistreMetiers(String activitePrincipaleRegistreMetiers) {
    this.activitePrincipaleRegistreMetiers = activitePrincipaleRegistreMetiers;
  }

  public String getAdresseComplement() {
    return adresseComplement;
  }

  public void setAdresseComplement(String adresseComplement) {
    this.adresseComplement = adresseComplement;
  }

  public String getAdresseNumeroVoie() {
    return adresseNumeroVoie;
  }

  public void setAdresseNumeroVoie(String adresseNumeroVoie) {
    this.adresseNumeroVoie = adresseNumeroVoie;
  }

  public String getAdresseIndiceRepetition() {
    return adresseIndiceRepetition;
  }

  public void setAdresseIndiceRepetition(String adresseIndiceRepetition) {
    this.adresseIndiceRepetition = adresseIndiceRepetition;
  }

  public String getAdresseTypeVoie() {
    return adresseTypeVoie;
  }

  public void setAdresseTypeVoie(String adresseTypeVoie) {
    this.adresseTypeVoie = adresseTypeVoie;
  }

  public String getAdresseLibelleVoie() {
    return adresseLibelleVoie;
  }

  public void setAdresseLibelleVoie(String adresseLibelleVoie) {
    this.adresseLibelleVoie = adresseLibelleVoie;
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

  public String getCodeCommune() {
    return codeCommune;
  }

  public void setCodeCommune(String codeCommune) {
    this.codeCommune = codeCommune;
  }

  public String getAdresseIdentifiant() {
    return adresseIdentifiant;
  }

  public void setAdresseIdentifiant(String adresseIdentifiant) {
    this.adresseIdentifiant = adresseIdentifiant;
  }

  public String getDateCreationEtablissement() {
    return dateCreationEtablissement;
  }

  public void setDateCreationEtablissement(String dateCreationEtablissement) {
    this.dateCreationEtablissement = dateCreationEtablissement;
  }

  public String getDateDernierTraitementEtablissement() {
    return dateDernierTraitementEtablissement;
  }

  public void setDateDernierTraitementEtablissement(String dateDernierTraitementEtablissement) {
    this.dateDernierTraitementEtablissement = dateDernierTraitementEtablissement;
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

  public List<String> getMatchedKeywords() {
    return matchedKeywords;
  }

  public void setMatchedKeywords(List<String> matchedKeywords) {
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