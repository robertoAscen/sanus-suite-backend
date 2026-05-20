package com.mx.asc.sanus_suite_backend.expedientes.services;

import com.mx.asc.sanus_suite_backend.expedientes.entities.Expediente;
import com.mx.asc.sanus_suite_backend.pacientes.entities.Paciente;

import java.util.Optional;

public interface ExpedienteService {
  /**
   * Crea un nuevo expediente para un paciente recién registrado.
   */
  Expediente crearExpedienteBase(Paciente paciente, String tenantId);

  /**
   * Valida si el paciente ya tiene una Historia Clínica inicial.
   */
  boolean tieneHistoriaClinica(Long expedienteId);

  /**
   * Recupera el expediente completo incluyendo el historial de notas.
   */
  //ExpedienteDto obtenerHistorialCompleto(Long pacienteId, String tenantId);

  Optional<Expediente> findByPacienteIdAndTenantId(Long pacienteId, String tenantId);
}
