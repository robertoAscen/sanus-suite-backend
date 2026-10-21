package com.mx.asc.sanus_suite_backend.citas.dtos;

import com.mx.asc.sanus_suite_backend.citas.enums.EstadoCitaEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CambiarEstadoCitaDto {
  @NotNull(message = "El ID de la cita es obligatorio")
  private Long citaId;

  @NotNull(message = "El nuevo estado es obligatorio")
  private EstadoCitaEnum nuevoEstado;
}