package com.mx.asc.sanus_suite_backend.pacientes.services;

import com.mx.asc.sanus_suite_backend.pacientes.entities.Paciente;
import com.mx.asc.sanus_suite_backend.util.responses.RespuestaApi;
import org.springframework.http.ResponseEntity;

public interface PacienteService {
  ResponseEntity<RespuestaApi> altaPaciente(Paciente paciente, String tenantId);
  ResponseEntity<RespuestaApi> listaPacientes(String tenantId);
  ResponseEntity<RespuestaApi> bajaPaciente(Long id, String tenantId);
}
