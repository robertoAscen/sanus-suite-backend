package com.mx.asc.sanus_suite_backend.citas.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CitaRequestDto {
  private Long id;

  @NotNull(message = "El paciente es obligatorio")
  private Long pacienteId;

  @NotNull(message = "La fecha y hora de inicio es obligatoria")
  private LocalDateTime fechaHoraInicio;

  @NotNull(message = "La fecha y hora de fin es obligatoria")
  private LocalDateTime fechaHoraFin;

  private String motivoConsulta;
  private String observaciones;
}