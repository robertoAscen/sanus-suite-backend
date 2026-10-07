package com.mx.asc.sanus_suite_backend.receta_medica.dtos;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class RecetaMedicaResponseDto {

  private Long id;
  private Long expedienteId;
  private Long notaEvolucionId;
  private Long pacienteId;
  private Long medicoId;
  private LocalDateTime fechaEmision;
  private String observacionesGenerales;

  // Control de Firma Legal y NOM-004
  private boolean firmado;
  private LocalDateTime fechaFirma;
  private String medicoNombreSnapshot;
  private String medicoCedulaSnapshot;

  // Lista de medicamentos
  private List<RecetaDetalleResponseDto> medicamentos;
}
