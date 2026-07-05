package com.myspacesante.insee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/* Bloc boot: demarrage principal du backend Spring Boot. */
@SpringBootApplication
@ConfigurationPropertiesScan
public class InseeSapBackendApplication {

  public static void main(String[] args) {
    SpringApplication.run(InseeSapBackendApplication.class, args);
  }
}