package com.mx.asc.sanus_suite_backend.handlers;

import com.mx.asc.sanus_suite_backend.expedientes.entities.Expediente;
import com.mx.asc.sanus_suite_backend.expedientes.services.ExpedienteService;
import com.mx.asc.sanus_suite_backend.pacientes.dtos.PacienteDto;
import com.mx.asc.sanus_suite_backend.pacientes.entities.Paciente;
import com.mx.asc.sanus_suite_backend.pacientes.mappers.PacienteMapper;
import com.mx.asc.sanus_suite_backend.pacientes.services.PacienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class PacienteHandler {

  private final PacienteService pacienteService;
  private final ExpedienteService expedienteService;
  private final PacienteMapper pacienteMapper;

  @Transactional
  public PacienteDto altaPaciente(Paciente paciente, String tenantId){
    Paciente newPaciente = pacienteService.altaPaciente(paciente, tenantId);
    Expediente newExpediente = expedienteService.crearExpedienteBase(newPaciente, tenantId);
    return pacienteMapper.toDto(newPaciente, newExpediente);
  }

  @Transactional(readOnly = true)
  public List<PacienteDto> listaPacientes(String tenantId){
    List<Paciente> pacientes = pacienteService.listaPacientes(tenantId);
    return pacientes.stream()
      .map(paciente -> {
        // Buscamos el expediente de manera opcional
        Expediente expediente = expedienteService.findByPacienteIdAndTenantId(paciente.getId(), tenantId)
          .orElse(null);
        // Mapeamos usando el componente especializado
        return pacienteMapper.toDto(paciente, expediente);
      })
      .collect(Collectors.toList());
  }

  @Transactional
  public void bajaLogicaPaciente(Long id, String tenantId){
    pacienteService.bajaPaciente(id, tenantId);
  }
}
