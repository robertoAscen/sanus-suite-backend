package com.mx.asc.sanus_suite_backend.receta_medica.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RecetaDetalleRequestDto {

  @NotNull(message = "El medicamento es obligatorio")
  private String medicamento;

  private String formaFarmaceutica;
  private String presentacion;

  @NotNull(message = "La dosis es obligatoria")
  private String dosis;

  @NotNull(message = "La vía de administración es obligatoria")
  private String viaAdministracion;

  @NotNull(message = "La frecuencia es obligatoria")
  private String frecuencia;

  @NotNull(message = "La duración es obligatoria")
  private String duracionTratamiento;

  private String indicacionesAdicionales;
}
