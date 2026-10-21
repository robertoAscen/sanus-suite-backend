package com.mx.asc.sanus_suite_backend.citas.services;

import com.mx.asc.sanus_suite_backend.citas.entities.Cita;
import com.mx.asc.sanus_suite_backend.citas.enums.EstadoCitaEnum;

import java.time.LocalDateTime;
import java.util.List;

public interface CitaService {
  Cita guardar(Cita entity);
  Cita obtenerPorIdYTenantId(Long id, String tenantId);
  Cita cambiarEstado(Long id, EstadoCitaEnum nuevoEstado, String tenantId);
  List<Cita> obtenerCitasPorRangoYMedico(Long medicoId, String tenantId, LocalDateTime inicio, LocalDateTime fin);
  List<Cita> obtenerCitasPorPaciente(Long pacienteId, String tenantId);
}