package com.myspacesante.insee.repository;

import com.myspacesante.insee.model.SapCompanyEntity;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/* Bloc repository: acces JPA pour lecture et mise a jour par SIRET. */
public interface SapCompanyRepository extends JpaRepository<SapCompanyEntity, Long> {

  Optional<SapCompanyEntity> findBySiret(String siret);

  java.util.List<SapCompanyEntity> findAllByOrderByDenominationAsc(Pageable pageable);
}