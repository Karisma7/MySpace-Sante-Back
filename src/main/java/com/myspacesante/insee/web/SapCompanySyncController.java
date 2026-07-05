package com.myspacesante.insee.web;

import com.myspacesante.insee.model.SapCompanyEntity;
import com.myspacesante.insee.service.SapCompanySyncService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/* Bloc REST: endpoints simples pour lancer la sync et lire la base. */
@Validated
@RestController
@RequestMapping("/api/sap-companies")
public class SapCompanySyncController {

  private final SapCompanySyncService service;

  public SapCompanySyncController(SapCompanySyncService service) {
    this.service = service;
  }

  @PostMapping("/sync")
  public SapCompanySyncService.SyncReport sync(
      @RequestParam(defaultValue = "5") @Min(1) @Max(100) int maxPages
  ) {
    return service.synchronize(maxPages);
  }

  @GetMapping
  public List<SapCompanyEntity> list(
      @RequestParam(defaultValue = "100") @Min(1) @Max(500) int limit
  ) {
    return service.list(limit);
  }
}