package com.mx.asc.sanus_suite_backend.citas.dtos;

import com.mx.asc.sanus_suite_backend.citas.enums.EstadoCitaEnum;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CitaResponseDto {
  private Long id;
  private Long pacienteId;
  private String pacienteNombre;
  private String pacienteTelefono;
  private Long expedienteId;
  private String numeroExpediente;
  private Long medicoId;
  private String medicoNombre;
  private LocalDateTime fechaHoraInicio;
  private LocalDateTime fechaHoraFin;
  private String motivoConsulta;
  private EstadoCitaEnum estado;
  private String observaciones;
  private Long notaEvolucionId;
}