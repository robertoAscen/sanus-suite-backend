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
public class AntecedentesPersonalesNoPatologicos {

  @Column(name = "apnp_habitos_higienicos", columnDefinition = "TEXT")
  private String habitosHigienicos;

  @Column(name = "apnp_alimentacion", columnDefinition = "TEXT")
  private String alimentacion;

  @Column(name = "apnp_vivienda", columnDefinition = "TEXT")
  private String vivienda;

  @Column(name = "apnp_actividad_fisica", columnDefinition = "TEXT")
  private String actividadFisica;

  @Column(name = "apnp_toxicomanias", columnDefinition = "TEXT")
  private String toxicomanias; // Tabaquismo, alcoholismo, etc.
}