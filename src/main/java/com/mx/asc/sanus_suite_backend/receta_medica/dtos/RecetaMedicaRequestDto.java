package com.mx.asc.sanus_suite_backend.receta_medica.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class RecetaMedicaRequestDto {

  @NotNull(message = "El ID del paciente es obligatorio")
  private Long pacienteId;

  private Long notaEvolucionId;

  private String observacionesGenerales;

  private Boolean firmado;

  @NotEmpty(message = "Debe incluir al menos un medicamento en la receta")
  private List<RecetaDetalleRequestDto> medicamentos;
}