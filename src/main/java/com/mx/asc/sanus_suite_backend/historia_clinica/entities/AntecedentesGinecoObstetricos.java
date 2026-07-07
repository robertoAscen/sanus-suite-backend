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
public class AntecedentesGinecoObstetricos {

  @Column(name = "ago_menarca")
  private Integer menarca; // Edad de primera menstruación

  @Column(name = "ago_ciclo_menstrual")
  private String cicloMenstrual; // Ej: "Regular, 28x5"

  @Column(name = "ago_gestas")
  private Integer gestas;

  @Column(name = "ago_partos")
  private Integer partos;

  @Column(name = "ago_cesareas")
  private Integer cesareas;

  @Column(name = "ago_abortos")
  private Integer abortos;

  @Column(name = "ago_fecha_ultima_menstruacion")
  private String fum; // Fecha Última Menstruación
}