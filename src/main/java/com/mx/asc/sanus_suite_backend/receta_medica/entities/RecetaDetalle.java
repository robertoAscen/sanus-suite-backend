package com.mx.asc.sanus_suite_backend.receta_medica.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "receta_detalles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecetaDetalle {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "medicamento", nullable = false)
  private String medicamento;

  @Column(name = "forma_farmaceutica")
  private String formaFarmaceutica;

  @Column(name = "presentacion")
  private String presentacion;

  @Column(name = "dosis", nullable = false)
  private String dosis;

  @Column(name = "via_administracion", nullable = false)
  private String viaAdministracion;

  @Column(name = "frecuencia", nullable = false)
  private String frecuencia;

  @Column(name = "duracion_tratamiento", nullable = false)
  private String duracionTratamiento;

  @Column(name = "indicaciones_adicionales", columnDefinition = "TEXT")
  private String indicacionesAdicionales;
}