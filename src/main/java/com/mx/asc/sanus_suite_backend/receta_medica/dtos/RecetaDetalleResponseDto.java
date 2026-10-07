package com.mx.asc.sanus_suite_backend.receta_medica.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RecetaDetalleResponseDto {

  private Long id;
  private String medicamento;
  private String formaFarmaceutica;
  private String presentacion;
  private String dosis;
  private String viaAdministracion;
  private String frecuencia;
  private String duracionTratamiento;
  private String indicacionesAdicionales;
}
