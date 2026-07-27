package com.mx.asc.sanus_suite_backend.historia_clinica.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AntecedentesHeredofamiliares {

  @Column(name = "ah_diabetes", columnDefinition = "TEXT")
  private String diabetes; // Ej: "Abuela materna, Madre"

  @Column(name = "ah_hipertension", columnDefinition = "TEXT")
  private String hipertension;

  @Column(name = "ah_cardiopatias", columnDefinition = "TEXT")
  private String cardiopatias;

  @Column(name = "ah_neoplasias", columnDefinition = "TEXT")
  private String neoplasias; // Cáncer

  @Column(name = "ah_otros", columnDefinition = "TEXT")
  private String otrosHereditarios;
}