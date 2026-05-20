package com.mx.asc.sanus_suite_backend.pacientes.services;

import com.mx.asc.sanus_suite_backend.pacientes.entities.Paciente;
import com.mx.asc.sanus_suite_backend.util.responses.RespuestaApi;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PacienteService {
  Paciente altaPaciente(Paciente paciente, String tenantId);
  List<Paciente> listaPacientes(String tenantId);
  void bajaPaciente(Long id, String tenantId);
}
