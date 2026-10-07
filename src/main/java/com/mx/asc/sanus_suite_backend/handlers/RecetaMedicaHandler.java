package com.mx.asc.sanus_suite_backend.handlers;

import com.mx.asc.sanus_suite_backend.expedientes.entities.Expediente;
import com.mx.asc.sanus_suite_backend.expedientes.services.ExpedienteService;
import com.mx.asc.sanus_suite_backend.medicos.entities.Medico;
import com.mx.asc.sanus_suite_backend.medicos.services.MedicoService;
import com.mx.asc.sanus_suite_backend.pacientes.entities.Paciente;
import com.mx.asc.sanus_suite_backend.pacientes.services.PacienteService;
import com.mx.asc.sanus_suite_backend.receta_medica.dtos.RecetaMedicaRequestDto;
import com.mx.asc.sanus_suite_backend.receta_medica.dtos.RecetaMedicaResponseDto;
import com.mx.asc.sanus_suite_backend.receta_medica.entities.RecetaMedica;
import com.mx.asc.sanus_suite_backend.receta_medica.mappers.RecetaMedicaMapper;
import com.mx.asc.sanus_suite_backend.receta_medica.services.RecetaMedicaService;
import com.mx.asc.sanus_suite_backend.receta_medica.services.impl.RecetaPdfService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;

@Component
@RequiredArgsConstructor
public class RecetaMedicaHandler {

  private final RecetaMedicaService recetaMedicaService;
  private final ExpedienteService expedienteService;
  private final MedicoService medicoService;
  private final RecetaMedicaMapper recetaMedicaMapper;
  private final PacienteService pacienteService;
  private final RecetaPdfService recetaPdfService;

  @Transactional
  public Long crearReceta(RecetaMedicaRequestDto dto, String tenantId, Long usuarioId) {

    Medico medico = medicoService.obtenerPorUserId(usuarioId, tenantId);
    Expediente expediente = expedienteService.findByPacienteIdAndTenantId(dto.getPacienteId(), tenantId);
    RecetaMedica entity = recetaMedicaMapper.toEntity(dto, expediente.getId(), medico.getId(), tenantId);

    if (Boolean.TRUE.equals(dto.getFirmado())) {
      recetaMedicaService.firmarReceta(medico, entity, tenantId);
    }

    RecetaMedica recetaGuardada = recetaMedicaService.guardar(entity, tenantId);
    return recetaGuardada.getId();
  }

  public RecetaMedicaResponseDto obtenerPorId(Long id, String tenantId, Long usuarioId) {
    RecetaMedica receta = recetaMedicaService.obtenerPorIdYTenantId(id, tenantId);
    Medico medico = medicoService.obtenerPorUserId(usuarioId, tenantId);
    return recetaMedicaMapper.toResponseDto(receta, medico);
  }

  public ByteArrayInputStream generarPdfReceta(Long recetaId, String tenantId, Long usuarioId) {
    RecetaMedica receta = recetaMedicaService.obtenerPorIdYTenantId(recetaId, tenantId);
    Paciente paciente = pacienteService.obtenerPacientePorIdAndTenantId(receta.getPacienteId(), tenantId);
    Medico medico = medicoService.obtenerPorUserId(usuarioId, tenantId);

    return recetaPdfService.generarPdfReceta(receta, paciente, medico);
  }
}
