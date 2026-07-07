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
public class AntecedentesPersonalesPatologicos {

  @Column(name = "app_enfermedades_cronicas", columnDefinition = "TEXT")
  private String enfermedadesCronicas;

  @Column(name = "app_alergias", columnDefinition = "TEXT")
  private String alergias;

  @Column(name = "app_quirurgicos", columnDefinition = "TEXT")
  private String quirurgicos;

  @Column(name = "app_traumaticos", columnDefinition = "TEXT")
  private String traumaticos;

  @Column(name = "app_transfusionales", columnDefinition = "TEXT")
  private String transfusionales;

  @Column(name = "app_otros", columnDefinition = "TEXT")
  private String otrosPatologicos;
}
