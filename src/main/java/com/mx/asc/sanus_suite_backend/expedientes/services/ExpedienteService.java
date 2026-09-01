package com.mx.asc.sanus_suite_backend.expedientes.services;

import com.mx.asc.sanus_suite_backend.expedientes.entities.Expediente;
import com.mx.asc.sanus_suite_backend.pacientes.entities.Paciente;

import java.util.Optional;

public interface ExpedienteService {
  Expediente crearExpedienteBase(Paciente paciente, String tenantId);
  Expediente findByPacienteIdAndTenantId(Long pacienteId, String tenantId);
  Expediente findByNumeroExpedienteAndTenantId(String numeroExpediente, String tenantId);
}
